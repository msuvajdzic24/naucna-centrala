package ftn.sep.services;

import java.util.List;

import ftn.sep.model.Edition;
import ftn.sep.model.OrderEdition;

public interface EditionService {

	public Edition getEdition(Long id);
	public List<Edition> getEditions();
	public boolean canBuyEdition(Long editionId, String username);
	public OrderEdition createOrderEdition(Long editionId, String username);
	public void confirmOrderEdition(String orderId);
	
}
