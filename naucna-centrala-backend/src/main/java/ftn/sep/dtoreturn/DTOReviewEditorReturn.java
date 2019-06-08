package ftn.sep.dtoreturn;

import ftn.sep.model.Review;

public class DTOReviewEditorReturn {
	
	private Long id;
	private String commentEditor;
	private String commentAuthor;
	private String suggestion;
	
	public DTOReviewEditorReturn() {
	}

	public DTOReviewEditorReturn(Long id, String commentEditor, String commentAuthor, String suggestion) {
		super();
		this.id = id;
		this.commentEditor = commentEditor;
		this.commentAuthor = commentAuthor;
		this.suggestion = suggestion;
	}
	
	public static DTOReviewEditorReturn convert(Review review) {
		DTOReviewEditorReturn dtoRE = new DTOReviewEditorReturn(review.getId(), review.getCommentEditor(), 
				review.getCommentAuthor(), review.getSuggestion());
		return dtoRE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
