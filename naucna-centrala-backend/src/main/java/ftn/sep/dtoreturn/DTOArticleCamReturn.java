package ftn.sep.dtoreturn;

import ftn.sep.model.Article;
import ftn.sep.model.Keyword;

public class DTOArticleCamReturn {

	private Long id;
	private String title;
	private String keywords;
	private String articleAbstract;
	private String area;
	private String journal;
	
	public DTOArticleCamReturn() {}

	public DTOArticleCamReturn(Long id, String title, String keywords, String articleAbstract, String area,
			String journal) {
		super();
		this.id = id;
		this.title = title;
		this.keywords = keywords;
		this.articleAbstract = articleAbstract;
		this.area = area;
		this.journal = journal;
	}

	public static DTOArticleCamReturn convert(Article article) {
        StringBuilder sb = new StringBuilder();
        boolean firstKeyword = true;
        for (Keyword keyword : article.getKeywords()) {
        	if (firstKeyword) {
        		firstKeyword = false;
        	} else {
        		sb.append(",");
        	}
			sb.append(keyword.getKeyword());
		}
        
		DTOArticleCamReturn dtoAR = new DTOArticleCamReturn(article.getId(), article.getTitle(),
				sb.toString(), article.getArticleAbstract(), article.getArea().getName(), article.getJournal().getName());
		return dtoAR;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getArticleAbstract() {
		return articleAbstract;
	}

	public void setArticleAbstract(String articleAbstract) {
		this.articleAbstract = articleAbstract;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}
	
	
}
