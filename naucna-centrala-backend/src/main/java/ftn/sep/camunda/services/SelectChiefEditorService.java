package ftn.sep.camunda.services;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Editor;
import ftn.sep.model.Journal;
import ftn.sep.repositories.JournalRepository;

@Service
public class SelectChiefEditorService implements JavaDelegate {
	
	@Autowired
	private JournalRepository journalRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long journalId = (Long) execution.getVariable("journalId");
		Journal journal = this.journalRepository.findById(journalId).get();
		Editor chiefEditor = journal.getChiefEditor();
		execution.setVariable("chiefEditor", chiefEditor.getUsername());
		
		List<String> users = new ArrayList<>();
		String authorUsername = (String) execution.getVariable("author");
		users.add(authorUsername);
		users.add(chiefEditor.getUsername());
		execution.setVariable("users", users);
		
	}

}
