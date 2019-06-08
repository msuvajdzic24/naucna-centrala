package ftn.sep.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ftn.sep.dto.DTOToken;
import ftn.sep.dto.DTOTokenForPayment;
import ftn.sep.dtoreturn.DTOArticleReturn;
import ftn.sep.exceptions.BadRequestException;
import ftn.sep.model.Article;
import ftn.sep.model.OrderArticle;
import ftn.sep.services.ArticleService;
import ftn.sep.services.JournalService;
import javassist.NotFoundException;

@RestController
public class ArticleController {
	
	@Value("${api}")
    private String api;
	
	@Value("${front}")
    private String frontUrl;
	
	@Value("${kp.api}")
    private String kpApi;
	
	@Value("${kp.front}")
    private String kpFront;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private JournalService journalService;
	
    @Autowired
    private HttpComponentsClientHttpRequestFactory hcchrf;
	
	@PreAuthorize("hasAuthority('get_article')")
	@RequestMapping(value = "/article/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> article(@PathVariable("id") Long id, Authentication authentication) {
	
		Article article = this.articleService.getArticle(id);
		DTOArticleReturn dtoAR = DTOArticleReturn.convert(article);
		dtoAR.setBuy(this.articleService.canBuyArticle(id, authentication.getName()));
		
		return new ResponseEntity<DTOArticleReturn>(dtoAR, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('get_articles')")
	@RequestMapping(value = "/articles", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> articles(Authentication authentication) {
		
		List<Article> articles = this.articleService.getArticles();
		List<DTOArticleReturn> dtoArticles = new ArrayList<>();
		for (Article article : articles) {
			DTOArticleReturn dtoAR = DTOArticleReturn.convert(article);
			dtoAR.setBuy(this.articleService.canBuyArticle(article.getId(), authentication.getName()));
			dtoArticles.add(dtoAR);
		}

		return new ResponseEntity<List<DTOArticleReturn>>(dtoArticles, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('buy_article')")
	@RequestMapping(value = "/article/{id}/buy", 
			method = RequestMethod.GET, 
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> buyArticle(@PathVariable("id") Long id, Authentication authentication) throws NotFoundException {
		
		boolean canBuy = this.articleService.canBuyArticle(id, authentication.getName());
		if (canBuy == false) {
			throw new BadRequestException("Article already bought!");
		}
		
		Article article = this.articleService.getArticle(id);
		
		String merchantId = this.journalService.getMerchantId(article.getEdition().getJournal().getId());
		if (merchantId == null) {
			throw new NotFoundException("Journal don't have merhcant id!");
		}
		
		OrderArticle oa = this.articleService.createOrderArticle(id, authentication.getName());
		
        RestTemplate rt = new RestTemplate(this.hcchrf);
		//RestTemplate rt = new RestTemplate();
        HttpHeaders hh = new HttpHeaders();
        hh.set("X-Auth-Token", merchantId);

        String redirectUrl = this.frontUrl + "/article/" + id;
        String callbackUrl = this.api + "/confirmOrderArticle/";
        DTOTokenForPayment dtoTFP = new DTOTokenForPayment(oa.getMerchantOrderId(), oa.getTimestamp(), oa.getPriceAmount(),
        		oa.getCurrency(), redirectUrl, callbackUrl);

        HttpEntity<DTOTokenForPayment> entity = new HttpEntity<DTOTokenForPayment>(dtoTFP, hh);

        DTOToken paymentToken = rt.postForObject(
                this.kpApi + "/auth/getPaymentToken", entity, DTOToken.class);

        System.out.println(paymentToken);
        return new ResponseEntity<>(this.kpFront + "/selectPaymentMethods/" + paymentToken.getToken(), hh, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/confirmOrderArticle/{orderId}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> subscribeArticle(@PathVariable("orderId") String orderId) {

		this.articleService.confirmOrderArticle(orderId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
