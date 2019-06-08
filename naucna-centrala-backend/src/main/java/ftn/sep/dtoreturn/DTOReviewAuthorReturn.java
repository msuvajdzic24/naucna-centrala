package ftn.sep.dtoreturn;

import ftn.sep.model.Review;

public class DTOReviewAuthorReturn {
	
	private Long id;
	private String comment;
	
	public DTOReviewAuthorReturn() {
	}

	public DTOReviewAuthorReturn(Long id, String comment) {
		super();
		this.id = id;
		this.comment = comment;
	}
	
	public static DTOReviewAuthorReturn convert(Review review) {
		DTOReviewAuthorReturn dtoRA = new DTOReviewAuthorReturn(review.getId(), review.getCommentAuthor());
		return dtoRA;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	

}
