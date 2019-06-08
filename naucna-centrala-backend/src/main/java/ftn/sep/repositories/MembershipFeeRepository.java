package ftn.sep.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.sep.model.MembershipFee;

import java.util.Optional;

@Repository
public interface MembershipFeeRepository extends CrudRepository<MembershipFee, Long> {

	MembershipFee findByPayerIdAndJournalId(Long id, Long jorunalId);
	MembershipFee findByPayerIdAndJournalIdAndPaidAndExpired(Long payerId, Long jorunalId, boolean paid, boolean expired);
	Optional<MembershipFee> findByMerchantOrderId(String merchantOrderId);

}
