package ftn.sep.dto;

import org.springframework.web.multipart.MultipartFile;

public class DTOFinalCorrection {
	
	private MultipartFile file;
	private String title;
	private String keywords;
	private String articleAbstract;
	private String authorMessage;
	
	public DTOFinalCorrection() {}

	public DTOFinalCorrection(MultipartFile file, String title, String keywords, String articleAbstract,
			String authorMessage) {
		super();
		this.file = file;
		this.title = title;
		this.keywords = keywords;
		this.articleAbstract = articleAbstract;
		this.authorMessage = authorMessage;
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

	public String getAuthorMessage() {
		return authorMessage;
	}

	public void setAuthorMessage(String authorMessage) {
		this.authorMessage = authorMessage;
	}

}
