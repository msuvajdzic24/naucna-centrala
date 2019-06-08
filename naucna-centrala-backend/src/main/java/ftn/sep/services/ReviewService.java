package ftn.sep.services;

import java.util.List;

import ftn.sep.model.PotentialReviewers;
import ftn.sep.model.Review;

public interface ReviewService {

	PotentialReviewers getPotentianlReviewers(Long articleId);
	List<Review> getReviews(Long articleId);

}
