package ftn.sep.dto;

public class DTOChooseNewReviewer {
	
	String reviewer;
	
	public DTOChooseNewReviewer() {
	}

	public DTOChooseNewReviewer(String reviewer) {
		super();
		this.reviewer = reviewer;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

}
