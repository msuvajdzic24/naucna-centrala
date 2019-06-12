package ftn.sep.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Article;
import ftn.sep.model.Journal;
import ftn.sep.model.User;
import ftn.sep.repositories.ArticleRepository;
import ftn.sep.repositories.JournalRepository;
import ftn.sep.repositories.UserRepository;

@Service
public class ChooseEditorService implements JavaDelegate {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private JournalRepository journalRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long articleId = (Long) execution.getVariable("articleId");
		Article article = this.articleRepository.findById(articleId).get();
		Long journalId = (Long) execution.getVariable("journalId");
		Journal journal = this.journalRepository.findById(journalId).get();
		User user = new User();
		
		boolean foundEditor = false;
		for (String editorArea : journal.getEditors().keySet()) {
			if (editorArea.equals(article.getArea().getName())) {
				// bira prvog editora na koga naidje iz tog casopisa da je u toj naucnoj oblasti
				String editorUsername = journal.getEditors().get(editorArea).getUsername();
				System.out.println("Izabrani urednik rada: " + editorUsername);
				execution.setVariable("editor", editorUsername);	
				user = this.userRepository.findByUsername(editorUsername).get();
				foundEditor = true;
				break;
			}
		}
		
		if (!foundEditor) {		// ako nije nasao odgovarajuceg urednika, setuje glavnog urednika casopisa kao urednika rada
			String editorUsername = journal.getChiefEditor().getUsername();
			System.out.println("Izabran je glavni urednik casopisa kao urednik rada: " + editorUsername);
			execution.setVariable("editor", editorUsername);
			user = this.userRepository.findByUsername(editorUsername).get();
		}
		
		// POSLATI MAIL EDITORU
		String text = "You were chosen as the editor of the article \"" + article.getTitle();
		this.mailService.sendMail(user.getEmail(), "Editor of the article", text);
	}

}
