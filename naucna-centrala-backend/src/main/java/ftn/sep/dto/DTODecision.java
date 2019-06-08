package ftn.sep.dto;

public class DTODecision {

	private boolean relevant;
	private boolean properlyFormatted;
	private String comment;
	
	public DTODecision() {}
	
	public DTODecision(boolean relevant, boolean properlyFormatted, String comment) {
		super();
		this.relevant = relevant;
		this.properlyFormatted = properlyFormatted;
		this.comment = comment;
	}
	
	public boolean isRelevant() {
		return relevant;
	}
	public void setRelevant(boolean relevant) {
		this.relevant = relevant;
	}
	public boolean isProperlyFormatted() {
		return properlyFormatted;
	}
	public void setProperlyFormatted(boolean properlyFormatted) {
		this.properlyFormatted = properlyFormatted;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
