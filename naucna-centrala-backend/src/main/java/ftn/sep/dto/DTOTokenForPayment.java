package ftn.sep.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ftn.sep.model.Currency;

public class DTOTokenForPayment {
	
	@NotNull(message = "Merchant order id can't be null!")
    private String merchantOrderId;

    @NotNull(message = "Timestamp can't be null!")
    private Date timestamp;

    @NotNull(message = "Timestamp can't be null!")
    private double amount;

    @NotNull(message = "Redirect url can't be null!")
    private String redirectUrl;

    @NotNull(message = "Callback url can't be null!")
    @Size(min = 1, message = "Callback url can't be null")
    private String callbackUrl;

    @NotNull(message = "Currency can't be null!")
    @Size(min = 1, message = "Currency can't be null")
    private Currency currency;

    public DTOTokenForPayment() {}

    public DTOTokenForPayment(String merchantOrderId, Date timestamp, double amount, Currency currency, String redirectUrl,
                              String callbackUrl) {
        this.merchantOrderId = merchantOrderId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.redirectUrl = redirectUrl;
        this.callbackUrl = callbackUrl;
        this.currency = currency;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
