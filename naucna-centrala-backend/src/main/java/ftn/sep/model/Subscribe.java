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

@Entity
public class Subscribe implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String merhcantOrderid;
	
	@Column
	private Long subscriberId;
	
	@Column
	private Long journalId;
	
	@Column
	private double priceAmount;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	@Column
	private Date timestamp;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private BillingPlanType billingPlanType;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private BillingPlanFrequency billingPlanFrequency;
	
	@Column
	private int cycles;
	
	@Column
	private String billingAgreementId;
	
	@Column
	private boolean active;
	
	public Subscribe() {
	}

	public Subscribe(Long subscriberId, Long journalId, double priceAmount, Currency currency, Date timestamp,
			BillingPlanType billingPlanType, BillingPlanFrequency billingPlanFrequency, int cycles) {
		super();
		this.subscriberId = subscriberId;
		this.journalId = journalId;
		this.priceAmount = priceAmount;
		this.currency = currency;
		this.timestamp = timestamp;
		this.billingPlanType = billingPlanType;
		this.billingPlanFrequency = billingPlanFrequency;
		this.cycles = cycles;
		this.active = false;
	}

	public Subscribe(Long id, String merhcantOrderid, Long subscriberId, Long journalId, double priceAmount,
			Currency currency, Date timestamp, BillingPlanType billingPlanType,
			BillingPlanFrequency billingPlanFrequency, int cycles, String billingAgreementId, boolean active) {
		super();
		this.id = id;
		this.merhcantOrderid = merhcantOrderid;
		this.subscriberId = subscriberId;
		this.journalId = journalId;
		this.priceAmount = priceAmount;
		this.currency = currency;
		this.timestamp = timestamp;
		this.billingPlanType = billingPlanType;
		this.billingPlanFrequency = billingPlanFrequency;
		this.cycles = cycles;
		this.billingAgreementId = billingAgreementId;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMerhcantOrderid() {
		return merhcantOrderid;
	}

	public void setMerhcantOrderid(String merhcantOrderid) {
		this.merhcantOrderid = merhcantOrderid;
	}

	public Long getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(Long subscriberId) {
		this.subscriberId = subscriberId;
	}

	public Long getJournalId() {
		return journalId;
	}

	public void setJournalId(Long journalId) {
		this.journalId = journalId;
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

	public BillingPlanType getBillingPlanType() {
		return billingPlanType;
	}

	public void setBillingPlanType(BillingPlanType billingPlanType) {
		this.billingPlanType = billingPlanType;
	}

	public BillingPlanFrequency getBillingPlanFrequency() {
		return billingPlanFrequency;
	}

	public void setBillingPlanFrequency(BillingPlanFrequency billingPlanFrequency) {
		this.billingPlanFrequency = billingPlanFrequency;
	}

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}

	public String getBillingAgreementId() {
		return billingAgreementId;
	}

	public void setBillingAgreementId(String billingAgreementId) {
		this.billingAgreementId = billingAgreementId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
