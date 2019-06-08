package ftn.sep.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import ftn.sep.dto.DTOBillingPlan;
import ftn.sep.dto.DTOToken;
import ftn.sep.dto.DTOTokenForPayment;
import ftn.sep.dtoreturn.DTOJournalReturn;
import ftn.sep.exceptions.NotFoundException;
import ftn.sep.model.Journal;
import ftn.sep.model.MembershipFee;
import ftn.sep.model.Role;
import ftn.sep.model.Subscribe;
import ftn.sep.services.AuthenticationService;
import ftn.sep.services.JournalService;


@RestController
public class JournalController {
	
	@Value("${api}")
    private String api;
	
	@Value("${front}")
    private String frontUrl;
	
	@Value("${kp.api}")
    private String kpApi;
	
	@Value("${kp.front}")
    private String kpFront;
	
	@Autowired
	private JournalService journalService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
    @Autowired
    private HttpComponentsClientHttpRequestFactory hcchrf;
	
	@PreAuthorize("hasAuthority('get_journal')")
	@RequestMapping(value = "/journal/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> journal(@PathVariable("id") Long id, Authentication authentication) {
		
		Journal journal = this.journalService.getJournal(id);
		DTOJournalReturn dtoJR = DTOJournalReturn.convert(journal);
		Role r = this.authenticationService.getUserRole(authentication.getName());
		if (r.getName().equalsIgnoreCase("author")) {
			dtoJR.setPayMembershipFee(this.journalService.canPayMembershipFee(id, authentication.getName()));
			dtoJR.setSubscribe(false);
		} else {
			dtoJR.setSubscribe(this.journalService.canSubscribe(id, authentication.getName()));
			dtoJR.setPayMembershipFee(false);
		}

		return new ResponseEntity<DTOJournalReturn>(dtoJR, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('get_journals')")
	@RequestMapping(value = "/journals", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> journals(Authentication authentication) {
		
		List<Journal> journals = this.journalService.getJournals();
		Role r = this.authenticationService.getUserRole(authentication.getName());
		List<DTOJournalReturn> dtoJournals = new ArrayList<>(); 
		if (r.getName().equalsIgnoreCase("author")) {
			for (Journal journal : journals) {
				DTOJournalReturn dtoJR = DTOJournalReturn.convert(journal);
				dtoJR.setSubscribe(false);
				dtoJR.setPayMembershipFee(this.journalService.canPayMembershipFee(journal.getId(), authentication.getName()));
				dtoJournals.add(dtoJR);
			}
		} else {
			for (Journal journal : journals) {
				DTOJournalReturn dtoJR = DTOJournalReturn.convert(journal);
				dtoJR.setSubscribe(this.journalService.canSubscribe(journal.getId(), authentication.getName()));
				dtoJR.setPayMembershipFee(false);
				dtoJournals.add(dtoJR);
			}
		}

		return new ResponseEntity<List<DTOJournalReturn>>(dtoJournals, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('get_journal_editions')")
	@RequestMapping(value = "/journal/{id}/editions", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getJournalEditions(@PathVariable("id") Long id, Authentication authentication) {

		return new ResponseEntity<String>("A", HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('subscribe_journal')")
	@RequestMapping(value = "/journal/{id}/subscribe", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> subscribeJournal(@PathVariable("id") Long id, Authentication authentication) {
	
		String merchantId = this.journalService.getMerchantId(id);
		if (merchantId == null) {
			throw new NotFoundException("Journal don't have merhcant id!");
		}
		
		Subscribe s = this.journalService.createSubscribe(id, authentication.getName()); 

		RestTemplate rt = new RestTemplate(this.hcchrf);
		//RestTemplate rt = new RestTemplate();
        HttpHeaders hh = new HttpHeaders();
        hh.set("X-Auth-Token", merchantId);

        String redirectUrl = this.frontUrl + "/journal/" + id;
        String callbackUrl = this.api + "/subscriptionConfirmations";
        DTOBillingPlan dtoBP = new DTOBillingPlan(s.getBillingPlanType(), s.getBillingPlanFrequency(), s.getCycles(),
        		s.getCurrency(), s.getPriceAmount(), redirectUrl, callbackUrl);
        dtoBP.setMerchantOrderId(s.getMerhcantOrderid());

        HttpEntity<DTOBillingPlan> entity = new HttpEntity<DTOBillingPlan>(dtoBP, hh);

        String redUrl = rt.postForObject(this.kpApi + "/paypal/subscribe", entity, String.class);

        return new ResponseEntity<>(redUrl, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('unsubscribe_journal')")
	@RequestMapping(value = "/journal/{id}/unsubcribe", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> unsubscribeJournal(@PathVariable("id") Long id, Authentication authentication) {



		String merchantId = this.journalService.getMerchantId(id);
		if (merchantId == null) {
			throw new NotFoundException("Journal don't have merhcant id!");
		}
		
		String agreementId = this.journalService.unsubscribeJournal(id, authentication.getName());
		
        RestTemplate rt = new RestTemplate(this.hcchrf);
		//RestTemplate rt = new RestTemplate();
        HttpHeaders hh = new HttpHeaders();
        hh.set("X-Auth-Token", merchantId);

        @SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity entity = new HttpEntity(hh);

        try {
			@SuppressWarnings("unused")
			String redirectUrl = rt.postForObject(this.kpApi + "/paypal/cancelSubscription/" + agreementId, entity, String.class);
		} catch (HttpStatusCodeException exception) {
			//
		}

        return new ResponseEntity<>(this.frontUrl + "/journal/" + id, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/subscriptionConfirmations/{id}/{agreementId}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> confirmSubscription(@PathVariable("id") String id, @PathVariable("agreementId") String agreementId) {
	
        this.journalService.confirmSubscription(id, agreementId);

        return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('membership_fee_journal')")
	@RequestMapping(value = "/journal/{id}/payMembershipFee", 
			method = RequestMethod.GET, 
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> payMembershipFee(@PathVariable("id") Long id, Authentication authentication) throws NotFoundException {

		String merchantId = this.journalService.getMerchantId(id);
		if (merchantId == null) {
			throw new NotFoundException("Journal don't have merhcant id!");
		}
		
		MembershipFee mf = this.journalService.createPayMembershipFee(id, authentication.getName());
		
        RestTemplate rt = new RestTemplate(this.hcchrf);
		//RestTemplate rt = new RestTemplate();
        HttpHeaders hh = new HttpHeaders();
        hh.set("X-Auth-Token", merchantId);

        String redirectUrl = this.frontUrl + "/journal/" + id;
        String callbackUrl = this.api + "/confirmMembershipFee/";
        DTOTokenForPayment dtoTFP = new DTOTokenForPayment(mf.getMerchantOrderId(), mf.getTimestamp(), mf.getPriceAmount(),
        		mf.getCurrency(), redirectUrl, callbackUrl);

        HttpEntity<DTOTokenForPayment> entity = new HttpEntity<DTOTokenForPayment>(dtoTFP, hh);

        DTOToken paymentToken = rt.postForObject(
                this.kpApi + "/auth/getPaymentToken", entity, DTOToken.class);

        System.out.println(paymentToken);
        return new ResponseEntity<>(this.kpFront + "/selectPaymentMethods/" + paymentToken.getToken(), hh, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/confirmMembershipFee/{orderId}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> subscribeJournal(@PathVariable("orderId") String orderId) {

		this.journalService.confirmMembershipFee(orderId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
