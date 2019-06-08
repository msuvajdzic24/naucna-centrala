package ftn.sep.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Article article;
	
	@ManyToOne
	private User reviewer;
	
	@Column(nullable = false)
	private String commentEditor;
	
	@Column(nullable = false)
	private String commentAuthor;
	
	@Column(nullable = false)
	private String suggestion;
	
	@Column
	private boolean old;
	
	public Review() {
	}

	public Review(Article article, User reviewer, String commentEditor, String commentAuthor, String suggestion) {
		super();
		this.article = article;
		this.reviewer = reviewer;
		this.commentEditor = commentEditor;
		this.commentAuthor = commentAuthor;
		this.suggestion = suggestion;
		this.old = false;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	public String getCommentEditor() {
		return commentEditor;
	}

	public void setCommentEditor(String commentEditor) {
		this.commentEditor = commentEditor;
	}

	public String getCommentAuthor() {
		return commentAuthor;
	}

	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public boolean isOld() {
		return old;
	}

	public void setOld(boolean old) {
		this.old = old;
	}
	
}
