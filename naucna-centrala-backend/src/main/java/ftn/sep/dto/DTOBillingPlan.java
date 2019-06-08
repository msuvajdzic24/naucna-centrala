package ftn.sep.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ftn.sep.model.BillingPlanFrequency;
import ftn.sep.model.BillingPlanType;
import ftn.sep.model.Currency;

public class DTOBillingPlan {
	
    @NotNull(message = "Merchant order id can't be null!")
    private String merchantOrderId;

    @NotNull(message = "Timestamp can't be null!")
    private Date timestamp;

    @NotNull(message = "Currency can't be null!")
    private BillingPlanType billingPlanType;

    @NotNull(message = "Currency can't be null!")
    private BillingPlanFrequency billingPlanFrequency;

    @NotNull(message = "Currency can't be null!")
    private int cycles;

    @NotNull(message = "Currency can't be null!")
    private Currency currency;

    @NotNull(message = "Amount can't be null!")
    private double amount;

    @NotNull(message = "Redirect url can't be null!")
    @Size(min = 1, message = "Redirect url can't be null")
    private String redirectUrl;

    @NotNull(message = "Callback url can't be null!")
    @Size(min = 1, message = "Callback url can't be null")
    private String callbackUrl;

    public DTOBillingPlan() {}

    public DTOBillingPlan(BillingPlanType billingPlanType, BillingPlanFrequency billingPlanFrequency, int cycles,
                          Currency currency, double amount, String redirectUrl, String callbackUrl) {
        this.billingPlanType = billingPlanType;
        this.billingPlanFrequency = billingPlanFrequency;
        this.cycles = cycles;
        this.currency = currency;
        this.amount = amount;
        this.redirectUrl = redirectUrl;
        this.callbackUrl = callbackUrl;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

}
