package ftn.sep.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ftn.sep.model.Journal;
import ftn.sep.model.MembershipFee;
import ftn.sep.model.Subscribe;

@Service
public interface JournalService {
	
	public Journal getJournal(Long id);
	public List<Journal> getJournals();
	public String getMerchantId(Long id);
	public boolean canPayMembershipFee(Long journalId, String username);
	public MembershipFee createPayMembershipFee(Long journalId, String username);
	public void confirmMembershipFee(String orderId);
	public boolean canSubscribe(Long journalId, String username);
	public Subscribe createSubscribe(Long journalId, String username);
	public void confirmSubscription(String subscribeId, String agreementId);
	public String unsubscribeJournal(Long journalId, String username);
}
