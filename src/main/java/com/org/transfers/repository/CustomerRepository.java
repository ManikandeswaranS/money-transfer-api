package com.org.transfers.repository;

import com.org.transfers.domain.CustomerDetails;
import com.org.transfers.exception.AccountException;

import java.util.List;

/**
 * The interface Customer repository.
 */
public interface CustomerRepository {

    /**
     * Gets all customers.
     *
     * @return the all customers
     * @throws AccountException the account exception
     */
    List<CustomerDetails> getAllCustomers() throws AccountException;

    /**
     * Gets customer by id.
     *
     * @param customerId the customer id
     * @return the customer by id
     * @throws AccountException the account exception
     */
    CustomerDetails getCustomerById(long customerId) throws AccountException;

    /**
     * Gets customer by name.
     *
     * @param customerName the customer name
     * @return the customer by name
     * @throws AccountException the account exception
     */
    CustomerDetails getCustomerByName(String customerName) throws AccountException;

    /**
     * Insert customer long.
     *
     * @param customerDetails the customer details
     * @return the string customer name
     * @throws AccountException the account exception
     */
    String insertCustomer(CustomerDetails customerDetails) throws AccountException;

    /**
     * Update customer int.
     *
     * @param customerId      the customer id
     * @param customerDetails the customer details
     * @return the int
     * @throws AccountException the account exception
     */
    int updateCustomer(long customerId, CustomerDetails customerDetails) throws AccountException;

    /**
     * Delete customer int.
     *
     * @param customerId the customer id
     * @return the int
     * @throws AccountException the account exception
     */
    int deleteCustomer(long customerId) throws AccountException;

}
