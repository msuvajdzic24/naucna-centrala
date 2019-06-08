package ftn.sep.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MembershipFee extends Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long journalId;
	
	@Column(nullable = false)
	private int duration;
	
	@Column(nullable = false)
	private boolean expired;
	
	public MembershipFee() {
	}

	public MembershipFee(Long payerId, double priceAmount, Currency currency, Date timestamp, Long journalId,
			int duration, boolean expired) {
		super(payerId, priceAmount, currency, timestamp);
		this.journalId = journalId;
		this.duration = duration;
		this.expired = expired;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJournalId() {
		return journalId;
	}

	public void setJournalId(Long journalId) {
		this.journalId = journalId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

}
