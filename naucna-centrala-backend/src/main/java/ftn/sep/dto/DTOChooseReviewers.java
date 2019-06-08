package ftn.sep.dto;

import java.util.ArrayList;
import java.util.List;

public class DTOChooseReviewers {

	List<String> reviewers;
	
	public DTOChooseReviewers() {
		this.reviewers = new ArrayList<>();
	}

	public DTOChooseReviewers(List<String> reviewers) {
		super();
		this.reviewers = reviewers;
	}

	public List<String> getReviewers() {
		return reviewers;
	}

	public void setReviewers(List<String> reviewers) {
		this.reviewers = reviewers;
	}
	
}
