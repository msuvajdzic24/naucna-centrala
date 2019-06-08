package ftn.sep.services.impl;

import java.util.Date;
import java.util.List;

import ftn.sep.model.JournalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.exceptions.BadRequestException;
import ftn.sep.exceptions.NotFoundException;
import ftn.sep.model.Buyer;
import ftn.sep.model.Edition;
import ftn.sep.model.OrderEdition;
import ftn.sep.repositories.BuyerRepository;
import ftn.sep.repositories.EditionRepository;
import ftn.sep.repositories.OrderEditionRepository;
import ftn.sep.services.EditionService;
import ftn.sep.services.JournalService;

@Service
public class EditionServiceImpl implements EditionService {
	
	@Autowired
	private EditionRepository editionRepository;
	
	@Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private OrderEditionRepository orderEditionRepository;
	
	@Autowired
	private JournalService journalService;

	@Override
	public Edition getEdition(Long id) {
		return this.editionRepository.findById(id).orElse(null);
	}

	@Override
	public List<Edition> getEditions() {
		return (List<Edition>) this.editionRepository.findAll();
	}

	@Override
	public boolean canBuyEdition(Long editionId, String username) {
		Buyer buyer = this.buyerRepository.findByUsername(username);
		if (buyer == null) {
			throw new NotFoundException("Buyer not found!");
		}

		Edition edition = this.editionRepository.findById(editionId).orElseThrow(
				() -> new NotFoundException("Edition not found!"));

		if (edition.getJournal().getType() == JournalType.OPEN_ACCESS) {
			return false;
		}

        OrderEdition oe = this.orderEditionRepository.findByPayerIdAndEditionIdAndPaid(buyer.getId(), editionId, true);
        if (oe != null) {
        	return false;
        }
		
        boolean sub = this.journalService.canSubscribe(edition.getJournal().getId(), username);
		return sub;
	}

	@Override
	public OrderEdition createOrderEdition(Long editionId, String username) {
		boolean canBuy = this.canBuyEdition(editionId, username);
		if (canBuy == false) {
			throw new BadRequestException("Edition already bought!");
		}
		
		Buyer buyer = this.buyerRepository.findByUsername(username);

		Edition edition = this.editionRepository.findById(editionId).orElseThrow(
				() -> new NotFoundException("Edition not found!"));
		
		OrderEdition oe = new OrderEdition(buyer.getId(), edition.getJournal().getEditionPrice(),
				edition.getJournal().getCurrency(), new Date(), editionId);
		this.orderEditionRepository.save(oe);
        // TODO: pogledati da nije slucajno oa.getId() = null u ovom slucaju
		String merhcantOrderId = "OE" + oe.getId();
		oe.setMerchantOrderId(merhcantOrderId); 
		this.orderEditionRepository.save(oe);
		return oe;
	}

	@Override
	public void confirmOrderEdition(String orderId) {
		OrderEdition oe = this.orderEditionRepository.findByMerchantOrderId(orderId).orElse(null);
		System.out.println(orderId);
		if (oe != null) {
			System.out.println(orderId+"!");
			oe.setPaid(true);
			this.orderEditionRepository.save(oe);
		}
		return;
	}

}
