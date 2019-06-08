package ftn.sep.camunda.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Article;
import ftn.sep.model.User;
import ftn.sep.repositories.ArticleRepository;
import ftn.sep.repositories.UserRepository;

@Service
public class ChooseNewReviewerNotificationService implements JavaDelegate {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ArticleRepository ArticleRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String editorUsername = (String) execution.getVariable("editor");
		User user = this.userRepository.findByUsername(editorUsername).get();
		Long articleId = (Long) execution.getVariable("articleId");
		Article article = this.ArticleRepository.findById(articleId).get();
		String text = "You need to choose new reviewer for article \"" + article.getTitle() + "\".";
		this.mailService.sendMail(user.getEmail(), "New reviewer", text);
		
		String reviewerUsername = (String) execution.getVariable("reviewer");
		@SuppressWarnings("unchecked")
		List<String> reviewers = (List<String>) execution.getVariable("reviewers");
		reviewers.remove(reviewerUsername);
		execution.setVariable("reviewers", reviewers);
		
	}

}
