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
public class SubmissionNotificationService implements JavaDelegate {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ArticleRepository ArticleRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		/*Set<String> set = execution.getVariableNamesLocal();
		for (String string : set) {
			System.out.println("LOCAL VARIABLES: " + string);
		}
		System.out.println("LOCAL VARIABLE: " + execution.getVariableLocal("user"));
		System.out.println("LOCAL VARIABLE: " + execution.getVariableLocal("loopCounter"));*/
		
		String username = (String) execution.getVariableLocal("user");
		User user = this.userRepository.findByUsername(username).get();
		String authorUsername = (String) execution.getVariable("author");
		Long articleId = (Long) execution.getVariable("articleId");
		Article article = this.ArticleRepository.findById(articleId).get();
		String text = "Author " + authorUsername + " submit new article with title \"" + article.getTitle() + "\"";
		this.mailService.sendMail(user.getEmail(), "New article submitted", text);
	}

}
