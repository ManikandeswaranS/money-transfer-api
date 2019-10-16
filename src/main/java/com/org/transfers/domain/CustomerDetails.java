package com.org.transfers.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Customer details.
 */
public class CustomerDetails {

    @JsonIgnore
    private long customerId;

    @JsonProperty(required = true)
    private String customerName;

    @JsonProperty(required = true)
    private String email;

    @JsonProperty(required = true)
    private String country;

    /**
     * Instantiates a new Customer details.
     */
    public CustomerDetails() {
    }

    /**
     * Instantiates a new Customer details.
     *
     * @param customerName the customer name
     * @param email        the email
     * @param country      the country
     */
    public CustomerDetails(String customerName, String email, String country) {
        this.customerName = customerName;
        this.email = email;
        this.country = country;
    }

    /**
     * Instantiates a new Customer details.
     *
     * @param customerId   the customer id
     * @param customerName the customer name
     * @param email        the email
     * @param country      the country
     */
    public CustomerDetails(long customerId, String customerName, String email, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.country = country;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerDetails that = (CustomerDetails) o;

        if (customerId != that.customerId) return false;
        if (!customerName.equals(that.customerName)) return false;
        if (!email.equals(that.email)) return false;
        return country.equals(that.country);

    }

    @Override
    public int hashCode() {
        int result = (int) (customerId ^ (customerId >>> 32));
        result = 31 * result + customerName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CustomerDetails{"
                + "customerId=" + customerId
                + ", customerName='" + customerName + '\''
                + ", email='" + email + '\''
                + ", country='" + country + '\''
                + '}';
    }
}
