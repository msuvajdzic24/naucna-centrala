package ftn.sep.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.PotentialReviewers;
import ftn.sep.model.Review;
import ftn.sep.repositories.PotentialReviewersRepository;
import ftn.sep.repositories.ReviewRepository;
import ftn.sep.services.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private PotentialReviewersRepository potentialReviewersRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public PotentialReviewers getPotentianlReviewers(Long articleId) {
		
		PotentialReviewers pr = this.potentialReviewersRepository.findByArticleId(articleId);
		return pr;
	}

	@Override
	public List<Review> getReviews(Long articleId) {
		
		List<Review> reviews = this.reviewRepository.findByArticleIdAndOld(articleId, false);
		return reviews;
	}

}
