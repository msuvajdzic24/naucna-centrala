package ftn.sep.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Article;
import ftn.sep.model.User;
import ftn.sep.repositories.ArticleRepository;
import ftn.sep.repositories.UserRepository;

@Service
public class FinalCorrectionNotificationService implements JavaDelegate {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String authorUsername = (String) execution.getVariable("author");
		User user = this.userRepository.findByUsername(authorUsername).get();
		Long articleId = (Long) execution.getVariable("articleId");
		Article article = this.articleRepository.findById(articleId).get();
		String text = "You have 2 days to make final correction for article \"" + article.getTitle() + "\"."
				+ "You will able to see comments from reviewers.";
		this.mailService.sendMail(user.getEmail(), "Thematic unacceptable article", text);
		
	}

}
