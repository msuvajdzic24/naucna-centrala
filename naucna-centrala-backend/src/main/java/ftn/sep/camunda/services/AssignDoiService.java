package ftn.sep.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Article;
import ftn.sep.repositories.ArticleRepository;

@Service
public class AssignDoiService implements JavaDelegate {
	
	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Long articleId = (Long) execution.getVariable("articleId");
		Article article = this.articleRepository.findById(articleId).get();
		String doi = "10.1000/" + article.getId();
		article.setDoi(doi);
		
		System.out.println("Dodeljivanje doi-a zavrseno!");
		
	}

}
