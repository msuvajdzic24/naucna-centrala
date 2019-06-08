package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.Buyer;

@Repository
public interface BuyerRepository extends CrudRepository<Buyer, Long> {

	Buyer findByUsername(String username);

}
