package ftn.sep.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "order_table")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String merchantOrderId;
	
	@Column(nullable = true)
	private Long payerId;
	
	@Column(nullable = false)
	private double priceAmount;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Currency currency; 
	
	@Column(nullable = false)
	private Date timestamp;
	
	@Column(nullable = false)
	private boolean paid; 
	
	public Order() {
	}
	
	public Order(Long payerId, double priceAmount, Currency currency, Date timestamp) {
		super();
		this.payerId = payerId;
		this.priceAmount = priceAmount;
		this.currency = currency;
		this.timestamp = timestamp;
		this.paid = false;
	}

	public Order(String merchantOrderId, Long payerId, double priceAmount, Currency currency, Date timestamp) {
		super();
		this.merchantOrderId = merchantOrderId;
		this.payerId = payerId;
		this.priceAmount = priceAmount;
		this.currency = currency;
		this.timestamp = timestamp;
		this.paid = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public Long getPayerId() {
		return payerId;
	}

	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}

	public double getPriceAmount() {
		return priceAmount;
	}

	public void setPriceAmount(double priceAmount) {
		this.priceAmount = priceAmount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
}
