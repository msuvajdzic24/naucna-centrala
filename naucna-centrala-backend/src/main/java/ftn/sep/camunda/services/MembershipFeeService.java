package ftn.sep.camunda.services;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.Author;
import ftn.sep.model.Journal;
import ftn.sep.model.MembershipFee;
import ftn.sep.repositories.AuthorRepository;
import ftn.sep.repositories.JournalRepository;
import ftn.sep.repositories.MembershipFeeRepository;

@Service
public class MembershipFeeService implements JavaDelegate {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private JournalRepository jouranlRepository;
	
	@Autowired
	private MembershipFeeRepository membershipFeeRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String authorUsername = (String) execution.getVariable("author");
		Author author = this.authorRepository.findByUsername(authorUsername);
		
		Long journalId = (Long) execution.getVariable("journalId");
		Journal journal = this.jouranlRepository.findById(journalId).get();
		
		boolean payment = (boolean) execution.getVariable("payment");
		if (payment) {
			MembershipFee mf = new MembershipFee(author.getId(), journal.getEditionPrice(), journal.getCurrency(),
					new Date(), journalId, 3, false);
			mf.setPaid(true);
			this.membershipFeeRepository.save(mf);
		}
		
	}

	
}
