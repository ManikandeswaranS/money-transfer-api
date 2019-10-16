package com.org.transfers.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * The type Account details.
 */
public class AccountDetails {

    @JsonProperty(required = true)
    private long accountId;

    @JsonIgnore
    private String accountType;

    @JsonIgnore
    private boolean accountValid;

    @JsonProperty(required = true)
    private String customerName;

    @JsonProperty(required = true)
    private BigDecimal accountBalance;

    @JsonProperty(required = true)
    private String currencyCode;

    @JsonIgnore
    private int withDrawalLimit;

    /**
     * Instantiates a new Account details.
     */
    public AccountDetails() {
    }

    /**
     * Instantiates a new Account details.
     *
     * @param customerName   the customer name
     * @param accountBalance the account balance
     * @param currencyCode   the currency code
     */
    public AccountDetails(String customerName, BigDecimal accountBalance, String currencyCode) {
        this.customerName = customerName;
        this.accountBalance = accountBalance;
        this.currencyCode = currencyCode;
    }

    /**
     * Instantiates a new Account details.
     *
     * @param accountId      the account id
     * @param customerName   the customer name
     * @param accountBalance the account balance
     * @param currencyCode   the currency code
     */
    public AccountDetails(long accountId, String customerName, BigDecimal accountBalance, String currencyCode) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.accountBalance = accountBalance;
        this.currencyCode = currencyCode;
    }

    /**
     * Instantiates a new Account details.
     *
     * @param accountId      the account id
     * @param accountType    the account type
     * @param accountValid   the account valid
     * @param customerName   the customer name
     * @param accountBalance the account balance
     * @param currencyCode   the currency code
     * @param withDrawLimit  the with draw limit
     */
    public AccountDetails(long accountId, String accountType, boolean accountValid, String customerName, BigDecimal accountBalance, String currencyCode, int withDrawalLimit) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountValid = accountValid;
        this.customerName = customerName;
        this.accountBalance = accountBalance;
        this.currencyCode = currencyCode;
        this.withDrawalLimit = withDrawalLimit;
    }

    /**
     * Gets currency code.
     *
     * @return the currency code
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets currency code.
     *
     * @param currencyCode the currency code
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * Gets account id.
     *
     * @return the account id
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Sets account id.
     *
     * @param accountId the account id
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets account balance.
     *
     * @return the account balance
     */
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    /**
     * Sets account balance.
     *
     * @param accountBalance the account balance
     */
    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Gets account type.
     *
     * @return the account type
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Sets account type.
     *
     * @param accountType the account type
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * Is account valid boolean.
     *
     * @return the boolean
     */
    public boolean isAccountValid() {
        return accountValid;
    }

    /**
     * Sets account valid.
     *
     * @param accountValid the account valid
     */
    public void setAccountValid(boolean accountValid) {
        this.accountValid = accountValid;
    }

    /**
     * Gets with drawal limit.
     *
     * @return the with drawal limit
     */
    public int getWithDrawalLimit() {
        return withDrawalLimit;
    }

    /**
     * Sets with draw limit.
     *
     * @param withDrawalLimit the with draw limit
     */
    public void setWithDrawalLimit(int withDrawalLimit) {
        this.withDrawalLimit = withDrawalLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountDetails that = (AccountDetails) o;

        if (accountId != that.accountId) return false;
        if (accountValid != that.accountValid) return false;
        if (withDrawalLimit != that.withDrawalLimit) return false;
        if (!accountType.equals(that.accountType)) return false;
        if (!customerName.equals(that.customerName)) return false;
        if (!accountBalance.equals(that.accountBalance)) return false;
        return currencyCode.equals(that.currencyCode);

    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + accountType.hashCode();
        result = 31 * result + (accountValid ? 1 : 0);
        result = 31 * result + customerName.hashCode();
        result = 31 * result + accountBalance.hashCode();
        result = 31 * result + currencyCode.hashCode();
        result = 31 * result + withDrawalLimit;
        return result;
    }

    @Override
    public String toString() {
        return "AccountDetails{"
                + "accountId=" + accountId
                + ", accountType='" + accountType + '\''
                + ", accountValid=" + accountValid
                + ", customerName='" + customerName + '\''
                + ", accountBalance=" + accountBalance
                + ", currencyCode='" + currencyCode + '\''
                + ", withDrawalLimit=" + withDrawalLimit
                + '}';
    }
}
