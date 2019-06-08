package ftn.sep.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ftn.sep.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.exceptions.BadRequestException;
import ftn.sep.exceptions.NotFoundException;
import ftn.sep.repositories.AuthorRepository;
import ftn.sep.repositories.BuyerRepository;
import ftn.sep.repositories.JournalRepository;
import ftn.sep.repositories.MembershipFeeRepository;
import ftn.sep.repositories.SubscribeRepository;
import ftn.sep.services.JournalService;

@Service
public class JournalServiceImpl implements JournalService {
	
	@Autowired
	private JournalRepository journalRepository;
	
	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private MembershipFeeRepository membershipFeeRepository;
	
	@Autowired
	private SubscribeRepository subscribeRepository;

	@Override
	public Journal getJournal(Long id) {
		return this.journalRepository.findById(id).orElse(null);
	}

	@Override
	public List<Journal> getJournals() {
		return (List<Journal>) this.journalRepository.findAll();
	}

	@Override
	public String getMerchantId(Long id) {
		Journal journal = getJournal(id);
		if (journal == null) {
			throw new NotFoundException("Journal not found!");
		}
		return journal.getMerchantId();
	}

	@Override
	public boolean canPayMembershipFee(Long journalId, String username) {
		Author author = this.authorRepository.findByUsername(username);
		if (author == null) {
			throw new NotFoundException("Author not found!");
		}

		Journal journal = this.journalRepository.findById(journalId).orElseThrow(
				() -> new NotFoundException("Journal not found!"));

		if (journal.getType() == JournalType.SUBSCRIPTION) {
			return false;
		}
		
		MembershipFee mf = this.membershipFeeRepository.findByPayerIdAndJournalIdAndPaidAndExpired(author.getId(), journalId, true, false);
        if (mf != null) {
        	Date now = new Date();
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(mf.getTimestamp());
        	cal.add(Calendar.MONTH, mf.getDuration());
        	Date exp = cal.getTime();
        	if (now.after(exp)) {
        		mf.setExpired(true);
        		this.membershipFeeRepository.save(mf);
        		return true;
        	}
        	return false;
        }
		return true;
	}

	@Override
	public MembershipFee createPayMembershipFee(Long journalId, String username) {
		boolean canPay = this.canPayMembershipFee(journalId, username);
		if (canPay == false) {
			throw new BadRequestException("Membership already paid!");
		}
		
		// zbog spring securitya author sigurno nije null ovde
		Author author = this.authorRepository.findByUsername(username);
		
		Journal journal = this.journalRepository.findById(journalId).orElseThrow(
				() -> new NotFoundException("Journal not found!"));
		
		// DUZINA TRAJANJA CLANARINE JE ZAKUCANA NA JEDAN MESEC
		MembershipFee mf = new MembershipFee(author.getId(), journal.getEditionPrice(), journal.getCurrency(), 
				new Date(), journalId, 1, false);
		this.membershipFeeRepository.save(mf);
        // TODO: pogledati da nije slucajno mf.get() = null u ovom slucaju
		String merhcantOrderId = "MF" + mf.getId();
		mf.setMerchantOrderId(merhcantOrderId); 
		this.membershipFeeRepository.save(mf);
		return mf;
	}

	@Override
	public void confirmMembershipFee(String orderId) {
		MembershipFee mf = this.membershipFeeRepository.findByMerchantOrderId(orderId).orElse(null);
		if (mf != null) {
			mf.setPaid(true);
			this.membershipFeeRepository.save(mf);
		}
		return;
	}

	@Override
	public boolean canSubscribe(Long journalId, String username) {
		Buyer buyer = this.buyerRepository.findByUsername(username);
		if (buyer == null) {
			throw new NotFoundException("Author not found");
		}

		Journal journal = this.journalRepository.findById(journalId).orElseThrow(
				() -> new NotFoundException("Journal not found!"));

		if (journal.getType() == JournalType.OPEN_ACCESS) {
			return false;
		}
		
		Subscribe s = this.subscribeRepository.findBySubscriberIdAndJournalIdAndActive(buyer.getId(), journalId, true);
		if (s != null) {
			return false;
		}
		
		return true;
	}

	@Override
	public Subscribe createSubscribe(Long journalId, String username) {
		boolean canSubscribe = this.canSubscribe(journalId, username);
		if (canSubscribe == false) {
			throw new BadRequestException("User alreday subscribed on journal!");
		}

		Journal journal = this.journalRepository.findById(journalId).orElseThrow(
				() -> new NotFoundException("Journal not found!"));
		
		Buyer buyer = this.buyerRepository.findByUsername(username);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);

		// ZAKUCANE VREDNOSTI NEKIH PARAMETARA
		Subscribe s = new Subscribe(buyer.getId(), journalId, journal.getEditionPrice(), journal.getCurrency(), cal.getTime(),
				BillingPlanType.INFINITE, BillingPlanFrequency.DAY, 0);
		this.subscribeRepository.save(s);
		String merhcantOrderId = "SUB" + s.getId();
		s.setMerhcantOrderid(merhcantOrderId);
		this.subscribeRepository.save(s);
		
		return s;
	}

	@Override
	public void confirmSubscription(String merchantOrderId, String agreementId) {
		Subscribe s = this.subscribeRepository.findByMerhcantOrderid(merchantOrderId).orElseThrow(
				() -> new NotFoundException("Subscribe not found!"));
		
		s.setBillingAgreementId(agreementId);
		s.setActive(true);
		this.subscribeRepository.save(s);
		return;
	}

	@Override
	public String unsubscribeJournal(Long journalId, String username) {
		Buyer buyer = this.buyerRepository.findByUsername(username);

		Journal journal = this.journalRepository.findById(journalId).orElseThrow(
				() -> new NotFoundException("Journal not found!"));
		
		Subscribe s = this.subscribeRepository.findBySubscriberIdAndJournalIdAndActive(buyer.getId(), journal.getId(), true);
		
		String agreementId = s.getBillingAgreementId();
		s.setActive(false);
		this.subscribeRepository.save(s);
		return agreementId;
	}


	
}
