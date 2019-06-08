package ftn.sep.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class DTOSubmitArticle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private MultipartFile file;
	private String title;
	private String journal;
	private String keywords;
	private String articleAbstract;
	private String area;
	private String authors; // stringify json

	public DTOSubmitArticle() {
	}
	
	public DTOSubmitArticle(MultipartFile file, String title, String journal, String keywords, String articleAbstract,
			String area, String authors) {
		super();
		this.file = file;
		this.title = title;
		this.journal = journal;
		this.keywords = keywords;
		this.articleAbstract = articleAbstract;
		this.area = area;
		this.authors = authors;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
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

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}
	
}
