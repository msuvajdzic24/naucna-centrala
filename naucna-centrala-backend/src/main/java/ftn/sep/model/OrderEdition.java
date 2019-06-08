package ftn.sep.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderEdition extends Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long editionId;
	
	public OrderEdition(){
	}

	public OrderEdition(Long payerId, double priceAmount, Currency currency, Date timestamp, Long editionId) {
		super(payerId, priceAmount, currency, timestamp);
		this.editionId = editionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEditionId() {
		return editionId;
	}

	public void setEditionId(Long editionId) {
		this.editionId = editionId;
	}
	
}
