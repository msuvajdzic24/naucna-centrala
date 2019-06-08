package ftn.sep.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Article;
import ftn.sep.model.Journal;
import ftn.sep.repositories.ArticleRepository;
import ftn.sep.repositories.JournalRepository;

@Service
public class ChooseEditorService implements JavaDelegate {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private JournalRepository journalRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long articleId = (Long) execution.getVariable("articleId");
		Article article = this.articleRepository.findById(articleId).get();
		Long journalId = (Long) execution.getVariable("journalId");
		Journal journal = this.journalRepository.findById(journalId).get();
		
		boolean foundEditor = false;
		for (String editorArea : journal.getEditors().keySet()) {
			if (editorArea.equals(article.getArea().getName())) {
				execution.setVariable("editor", journal.getEditors().get(editorArea).getUsername());
				foundEditor = true;
				break;
			}
		}
		
		if (!foundEditor) {
			execution.setVariable("editor", journal.getChiefEditor().getUsername());
		}
		
		// POSLATI MAIL EDITORU
	}

}
