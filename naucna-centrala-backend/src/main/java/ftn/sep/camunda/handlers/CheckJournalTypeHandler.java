package ftn.sep.camunda.handlers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Journal;
import ftn.sep.repositories.JournalRepository;
import ftn.sep.services.JournalService;

@Service
public class CheckJournalTypeHandler implements TaskListener {
	
	@Autowired
	private JournalRepository journalRepository;
	
	@Autowired
	private JournalService journalService;

	@Override
	public void notify(DelegateTask delegateTask) {
		
		DelegateExecution execution =  delegateTask.getExecution();
		
		Long journalId = (Long) execution.getVariable("journalId");
		Journal journal = this.journalRepository.findById(journalId).get();
		String journalType = journal.getType().toString();
		execution.setVariable("journalType", journalType);
		
		if (journalType.equals("OPEN_ACCESS")) {
			String username = (String) execution.getVariable("author");
			boolean canPay = this.journalService.canPayMembershipFee(journalId, username);
			execution.setVariable("membershipFee", !canPay);
		}
	}

}
