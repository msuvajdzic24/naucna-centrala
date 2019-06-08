package ftn.sep.camunda.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Article;
import ftn.sep.model.Review;
import ftn.sep.model.User;
import ftn.sep.repositories.ArticleRepository;
import ftn.sep.repositories.ReviewRepository;
import ftn.sep.repositories.UserRepository;

@Service
public class SaveReviewService implements JavaDelegate {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long articleId = (Long) execution.getVariable("articleId");
		Article article = this.articleRepository.findById(articleId).get();
		
		String reviewerUsername = (String) execution.getVariable("reviewer");
		User reviewer = this.userRepository.findByUsername(reviewerUsername).get();
		
		Review review = new Review();
		review.setArticle(article);
		review.setReviewer(reviewer);
		review.setCommentEditor((String) execution.getVariable("commentEditor"));
		review.setCommentAuthor((String) execution.getVariable("commentAuthor"));
		review.setSuggestion((String) execution.getVariable("suggestion"));
		
		@SuppressWarnings("unchecked")
		List<String> reviewersFinish = (List<String>) execution.getVariable("reviewersFinish");
		reviewersFinish.add(reviewerUsername);
		execution.setVariable("reviewersFinish", reviewersFinish);

		this.reviewRepository.save(review);
		
	}

}
