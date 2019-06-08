package ftn.sep.services.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ftn.sep.dto.DTOResubmitArticle;
import ftn.sep.dto.DTOSubmitArticle;
import ftn.sep.exceptions.BadRequestException;
import ftn.sep.exceptions.NotFoundException;
import ftn.sep.model.Article;
import ftn.sep.model.Author;
import ftn.sep.model.Buyer;
import ftn.sep.model.CoAuthor;
import ftn.sep.model.Journal;
import ftn.sep.model.JournalType;
import ftn.sep.model.Keyword;
import ftn.sep.model.OrderArticle;
import ftn.sep.model.ScientificArea;
import ftn.sep.repositories.ArticleRepository;
import ftn.sep.repositories.AuthorRepository;
import ftn.sep.repositories.BuyerRepository;
import ftn.sep.repositories.CoAuthorRepository;
import ftn.sep.repositories.JournalRepository;
import ftn.sep.repositories.KeywordRepository;
import ftn.sep.repositories.OrderArticleRepository;
import ftn.sep.repositories.ScientificAreaRepository;
import ftn.sep.services.ArticleService;
import ftn.sep.services.EditionService;

@Service
public class ArticleServiceImpl implements ArticleService {
	
    //@Value("${filesDir}")
    //private String FILES_DIR;
    
    @Value("${storage}")
    private String FILES_DIR;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private ScientificAreaRepository areaRepository;
	
	@Autowired
	private JournalRepository journalRepository;
	
	@Autowired
	private CoAuthorRepository coAuthorRepository;
	
	@Autowired
	private KeywordRepository keywordRepository;
	
	@Autowired
	private OrderArticleRepository orderArticleRepository;

	@Autowired
	private EditionService editionService;

	@Override
	public Article getArticle(Long id) {
		return this.articleRepository.findById(id).orElse(null);
	}

	@Override
	public List<Article> getArticles() {
		return (List<Article>) this.articleRepository.findAll();
	}

	@Override
	public boolean canBuyArticle(Long articleId, String username) {
		Buyer buyer = this.buyerRepository.findByUsername(username);
		if (buyer == null) {
			throw new NotFoundException("Buyer not found!");
		}

		Article article = this.articleRepository.findById(articleId).orElseThrow(
				() -> new NotFoundException());

		if (article.getEdition().getJournal().getType() == JournalType.OPEN_ACCESS) {
			return false;
		}
		
		OrderArticle oa = this.orderArticleRepository.findByPayerIdAndArticleIdAndPaid(buyer.getId(), articleId, true);
        if (oa != null) {
        	return false;
        }
		
        boolean oe = this.editionService.canBuyEdition(article.getEdition().getId(), username);
        
		return oe;
	}

	@Override
	public OrderArticle createOrderArticle(Long articleId, String username) {
		boolean canBuy = this.canBuyArticle(articleId, username);
		if (canBuy == false) {
			throw new BadRequestException("Article already bought!");
		}
		
		Article article = this.articleRepository.findById(articleId).orElseThrow(
				() -> new NotFoundException());
		
		Buyer buyer = this.buyerRepository.findByUsername(username);
		
		OrderArticle oa = new OrderArticle(buyer.getId(), article.getEdition().getJournal().getArticlePrice(),
				article.getEdition().getJournal().getCurrency(), new Date(), articleId);
		this.orderArticleRepository.save(oa);
        // TODO: pogledati da nije slucajno oa.getId() = null u ovom slucaju
		String merhcantOrderId = "OA" + oa.getId();
		oa.setMerchantOrderId(merhcantOrderId); 
		this.orderArticleRepository.save(oa);
		return oa;
	}

	@Override
	public void confirmOrderArticle(String merchantOrderId) {
		// TODO: STA DA RADIMO AKO JE NEKI U PROCESU PLCANJA
		OrderArticle oa = this.orderArticleRepository.findByMerchantOrderId(merchantOrderId).orElse(null);
		if (oa != null) {
			oa.setPaid(true);
			this.orderArticleRepository.save(oa);
		}
		return;
	}
	
	@Override
	public Article submit(DTOSubmitArticle dtoSA, String username) {
		
		Article article = new Article();
		article.setTitle(dtoSA.getTitle());
		
		Author author = this.authorRepository.findByUsername(username);
		if (author == null) {
			throw new NotFoundException("Author not found!");
		}
		article.setSubmitter(author);
		
		ObjectMapper mapper = new ObjectMapper();
        try {
            Set<CoAuthor> coAuthors = mapper.readValue(dtoSA.getAuthors(), new TypeReference<Set<CoAuthor>>() {});
            Set<CoAuthor> authors = new HashSet<>();
            for (CoAuthor coAuthor : coAuthors) {
				this.coAuthorRepository.save(coAuthor);
				authors.add(coAuthor);
			}
            article.setAuthors(authors);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        article.setArticleAbstract(dtoSA.getArticleAbstract());
        String[] tokens = dtoSA.getKeywords().split(",");
        for (String token : tokens) {
        	Keyword keyword = new Keyword(token.trim());
        	this.keywordRepository.save(keyword);
			article.getKeywords().add(keyword);
		}

        ScientificArea area = this.areaRepository.findByName(dtoSA.getArea());
        if (area == null) {
        	throw new NotFoundException("Area not found!");
        }
        article.setArea(area);
        
        Journal journal = this.journalRepository.findByName(dtoSA.getJournal());
        if (journal == null) {
        	throw new NotFoundException("Journal not found!");
        }
        article.setJournal(journal);
		
        String filename;
		try {
			filename = saveFile(dtoSA.getFile());
		} catch (IOException e1) {
			throw new BadRequestException("Failed to upload file!");
		}
		
        article.setFilename(filename);

        this.articleRepository.save(article);
        
		return article;
	}
	
	private String saveFile(MultipartFile file) throws IOException {
    	String retVal = null;
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            System.out.println(file.getOriginalFilename());
            System.out.println(FILES_DIR);
            String filename = UUID.randomUUID().toString() + ".pdf";
            File f = new File(FILES_DIR + File.separator + filename);
            Path path = Paths.get(f.getAbsolutePath());
            Files.write(path, bytes);
            retVal = path.toString();
            System.out.println(retVal);
        }
        return retVal;
	}

	@Override
	public Resource loadFileAsResource(Long articleId) {
        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(() -> new BadRequestException("Article with that id does not exist!"));
        
        Path path = Paths.get(article.getFilename());
        Resource resource;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			throw new NotFoundException("Article file not found!");
		}
                
		return resource;
	}

	@Override
	public Article resubmit(Long articleId, DTOResubmitArticle dtoRA, String name) {
		Article article = this.articleRepository.findById(articleId).orElse(null);
		if (article == null) {
			throw new NotFoundException("Article not found!");
		}
		
		article.setTitle(dtoRA.getTitle());
		article.setArticleAbstract(dtoRA.getArticleAbstract());
		
		Set<Keyword> keywords = article.getKeywords();
		article.setKeywords(new HashSet<Keyword>());
		for (Keyword keyword: keywords) {
			this.keywordRepository.delete(keyword);
		}
		
        String[] tokens = dtoRA.getKeywords().split(",");
        for (String token : tokens) {
        	Keyword keyword = new Keyword(token.trim());
        	this.keywordRepository.save(keyword);
			article.getKeywords().add(keyword);
		}

    	String filename;
		try {
			filename = this.saveFile(dtoRA.getFile());
		} catch (IOException e) {
			throw new BadRequestException("Failed to upload file!");
		}
    	article.setFilename(filename);
        
		this.articleRepository.save(article);
        
		return article;
	}

}
