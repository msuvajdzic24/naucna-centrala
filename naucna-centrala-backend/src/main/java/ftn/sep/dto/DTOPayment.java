package ftn.sep.dto;

public class DTOPayment {
	
	boolean payment;
	
	public DTOPayment() {
	}

	public DTOPayment(boolean payment) {
		super();
		this.payment = payment;
	}

	public boolean isPayment() {
		return payment;
	}

	public void setPayment(boolean payment) {
		this.payment = payment;
	}

}
