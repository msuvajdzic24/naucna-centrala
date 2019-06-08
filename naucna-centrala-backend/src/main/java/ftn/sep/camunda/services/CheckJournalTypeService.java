package ftn.sep.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Journal;
import ftn.sep.repositories.JournalRepository;
import ftn.sep.services.JournalService;

@Service
public class CheckJournalTypeService implements JavaDelegate {

	@Autowired
	private JournalRepository journalRepository;
	
	@Autowired
	private JournalService journalService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
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
