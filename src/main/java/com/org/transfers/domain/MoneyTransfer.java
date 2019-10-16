package com.org.transfers.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * The type Money transfer.
 */
public class MoneyTransfer {

    @JsonProperty(required = true)
    private BigDecimal amount;

    @JsonProperty(required = true)
    private Long sourceAccountId;

    @JsonProperty(required = true)
    private Long targetAccountId;

    /**
     * Instantiates a new Money transfer.
     */
    public MoneyTransfer() {
    }

    /**
     * Instantiates a new Money transfer.
     *
     * @param amount          the amount
     * @param sourceAccountId the source account id
     * @param targetAccountId the target account id
     */
    public MoneyTransfer(BigDecimal amount, Long sourceAccountId, Long targetAccountId) {
        this.amount = amount;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets source account id.
     *
     * @return the source account id
     */
    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    /**
     * Sets source account id.
     *
     * @param sourceAccountId the source account id
     */
    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    /**
     * Gets target account id.
     *
     * @return the target account id
     */
    public Long getTargetAccountId() {
        return targetAccountId;
    }

    /**
     * Sets target account id.
     *
     * @param targetAccountId the target account id
     */
    public void setTargetAccountId(Long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyTransfer that = (MoneyTransfer) o;

        if (!amount.equals(that.amount)) return false;
        if (!sourceAccountId.equals(that.sourceAccountId)) return false;
        return targetAccountId.equals(that.targetAccountId);

    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + sourceAccountId.hashCode();
        result = 31 * result + targetAccountId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MoneyTransfer{"
                + "amount=" + amount
                + ", sourceAccountId=" + sourceAccountId
                + ", targetAccountId=" + targetAccountId
                + '}';
    }
}
