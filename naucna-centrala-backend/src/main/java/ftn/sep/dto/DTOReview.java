package ftn.sep.dto;

public class DTOReview {
	
	private String commentEditor;
	private String commentAuthor;
	private String suggestion;
	
	public DTOReview() {}

	public DTOReview(String commentEditor, String commentAuthor, String suggestion) {
		super();
		this.commentEditor = commentEditor;
		this.commentAuthor = commentAuthor;
		this.suggestion = suggestion;
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
