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
import org.springframework.web.client.RestTemplate;

import ftn.sep.dto.DTOToken;
import ftn.sep.dto.DTOTokenForPayment;
import ftn.sep.dtoreturn.DTOEditionReturn;
import ftn.sep.exceptions.BadRequestException;
import ftn.sep.model.Edition;
import ftn.sep.model.OrderEdition;
import ftn.sep.services.EditionService;
import ftn.sep.services.JournalService;
import javassist.NotFoundException;

@RestController
public class EditionController {
	
	@Value("${api}")
    private String api;
	
	@Value("${front}")
    private String frontUrl;
	
	@Value("${kp.api}")
    private String kpApi;
	
	@Value("${kp.front}")
    private String kpFront;
	
	@Autowired
	private EditionService editionService;
	
	@Autowired
	private JournalService journalService;
	
    @Autowired
    private HttpComponentsClientHttpRequestFactory hcchrf;
	
	@PreAuthorize("hasAuthority('get_edition')")
	@RequestMapping(value = "/edition/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> edition(@PathVariable("id") Long id, Authentication authentication) {

		Edition edition = this.editionService.getEdition(id);
		DTOEditionReturn dtoER = DTOEditionReturn.convert(edition);
		dtoER.setBuy(this.editionService.canBuyEdition(id, authentication.getName()));
		
		return new ResponseEntity<DTOEditionReturn>(dtoER, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('get_editions')")
	@RequestMapping(value = "/editions", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editions(Authentication authentication) {
		
		List<Edition> editions = this.editionService.getEditions();
		List<DTOEditionReturn> dtoEditions = new ArrayList<>();
		for (Edition edition : editions) {
			DTOEditionReturn dtoER = DTOEditionReturn.convert(edition);
			dtoER.setBuy(this.editionService.canBuyEdition(edition.getId(), authentication.getName()));
			dtoEditions.add(dtoER);
		}

		return new ResponseEntity<List<DTOEditionReturn>>(dtoEditions, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('get_edition_articles')")
	@RequestMapping(value = "/edition/{id}/articles", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editionArticles(@PathVariable("id") Long id, Authentication authentication) {

		Edition edition = this.editionService.getEdition(id);
		
		return new ResponseEntity<Edition>(edition, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('buy_edition')")
	@RequestMapping(value = "/edition/{id}/buy", 
			method = RequestMethod.GET, 
			produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> buyEdition(@PathVariable("id") Long id, Authentication authentication) throws NotFoundException {
		
		boolean canBuy = this.editionService.canBuyEdition(id, authentication.getName());
		if (canBuy == false) {
			throw new BadRequestException("Edition already bought!");
		}
		
		Edition edition = this.editionService.getEdition(id);
		
		String merchantId = this.journalService.getMerchantId(edition.getJournal().getId());
		if (merchantId == null) {
			throw new NotFoundException("Journal don't have merhcant id!");
		}
		
		OrderEdition oe = this.editionService.createOrderEdition(id, authentication.getName());
		
        RestTemplate rt = new RestTemplate(this.hcchrf);
		//RestTemplate rt = new RestTemplate();
        HttpHeaders hh = new HttpHeaders();
        hh.set("X-Auth-Token", merchantId);

        String redirectUrl = this.frontUrl + "/edition/" + id;
        String callbackUrl = this.api + "/confirmOrderEdition/";
        DTOTokenForPayment dtoTFP = new DTOTokenForPayment(oe.getMerchantOrderId(), oe.getTimestamp(), oe.getPriceAmount(),
        		oe.getCurrency(), redirectUrl, callbackUrl);

        HttpEntity<DTOTokenForPayment> entity = new HttpEntity<DTOTokenForPayment>(dtoTFP, hh);

        DTOToken paymentToken = rt.postForObject(
                this.kpApi + "/auth/getPaymentToken", entity, DTOToken.class);

        System.out.println(paymentToken);
        return new ResponseEntity<>(this.kpFront + "/selectPaymentMethods/" + paymentToken.getToken(), hh, HttpStatus.OK);
	}
	
	// TODO: PROVERITI STA JE PRODUCES
	@RequestMapping(value = "/confirmOrderEdition/{orderId}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> subscribeArticle(@PathVariable("orderId") String orderId) {

		this.editionService.confirmOrderEdition(orderId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
