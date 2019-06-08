package ftn.sep.camunda.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Review;
import ftn.sep.repositories.ReviewRepository;

@Service
public class DecisionHandler implements TaskListener {
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public void notify(DelegateTask delegateTask) {

		DelegateExecution execution =  delegateTask.getExecution();
		
		String decision = (String) execution.getVariable("decision");
		if (decision.equals("additional review")) {
			Integer numberOfReviewers = (Integer) execution.getVariable("numberOfReviewers");
			numberOfReviewers++;
			execution.setVariable("numberOfReviewers", numberOfReviewers);
		} else if (decision.equals("big correction")) {
			@SuppressWarnings("unchecked")
			List<String> reviewersFinish = (List<String>) execution.getVariable("reviewersFinish");
			reviewersFinish.clear();
			execution.setVariable("reviewersFinish", reviewersFinish);
			
			Long articleId = (Long) execution.getVariable("articleId");
			this.setReviewsToOld(articleId);
			
			execution.setVariable("firstCycle", false);
		} else if (decision.equals("small correction")) {
			execution.setVariable("firstCycle", false);
		}
		
	}
	
	private void setReviewsToOld(Long articleId) {
		List<Review> reviews = this.reviewRepository.findByArticleIdAndOld(articleId, false);
		for (Review review : reviews) {
			review.setOld(true);
			this.reviewRepository.save(review);
		}
	}

}
