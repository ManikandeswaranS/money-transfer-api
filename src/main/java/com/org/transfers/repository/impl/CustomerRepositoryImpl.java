package com.org.transfers.repository.impl;

import com.org.transfers.domain.CustomerDetails;
import com.org.transfers.exception.AccountException;
import com.org.transfers.repository.CustomerRepository;
import com.org.transfers.repository.H2RepositoryFactory;
import com.org.transfers.utils.Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * The type Customer repository.
 */
public class CustomerRepositoryImpl implements CustomerRepository {

    private static Logger log = Logger.getLogger(CustomerRepositoryImpl.class);

    @Override
    public List<CustomerDetails> getAllCustomers() throws AccountException {
        try (Connection connection = H2RepositoryFactory.getConnection()) {
            return new QueryRunner()
                    .query(connection, Utils.getStringProperty("sql_all_customers"), new BeanListHandler<>(CustomerDetails.class));
        } catch (SQLException e) {
            throw new AccountException("Exception occurred while reading all customer details", e);
        }
    }

    @Override
    public CustomerDetails getCustomerById(final long customerId) throws AccountException {
        try (Connection connection = H2RepositoryFactory.getConnection()) {
            CustomerDetails customerDetails = new QueryRunner()
                    .query(connection, Utils.getStringProperty("sql_customer_by_id"), new BeanHandler<>(CustomerDetails.class), customerId);
            if (log.isDebugEnabled()) {
                log.debug("Retrieved Customer: " + customerDetails);
            }
            return customerDetails;
        } catch (SQLException e) {
            throw new AccountException("Exception occurred while reading customer data", e);
        }
    }

    @Override
    public CustomerDetails getCustomerByName(final String customerName) throws AccountException {
        try (Connection connection = H2RepositoryFactory.getConnection()) {
            CustomerDetails customerDetails = new QueryRunner()
                    .query(connection, Utils.getStringProperty("sql_customer_by_name"), new BeanHandler<>(CustomerDetails.class), customerName);
            if (log.isDebugEnabled()) {
                log.debug("Retrieved Customer: " + customerDetails);
            }
            return customerDetails;
        } catch (SQLException e) {
            throw new AccountException("Exception occurred while reading customer data", e);
        }
    }

    @Override
    public String insertCustomer(final CustomerDetails customer) throws AccountException {
        try (Connection connection = H2RepositoryFactory.getConnection()) {
            int count = new QueryRunner()
                    .update(connection, Utils.getStringProperty("sql_customer_creation"), customer.getCustomerName(), customer.getEmail(), customer.getCountry());
            if (count == 1) {
                return customer.getCustomerName();
            }
        } catch (SQLException e) {
            throw new AccountException("Exception occurred while creating customer data", e);
        }
        return null;
    }

    @Override
    public int updateCustomer(final long customerId, final CustomerDetails customer) throws AccountException {
        try (Connection connection = H2RepositoryFactory.getConnection()) {
            return new QueryRunner()
                    .update(connection, Utils.getStringProperty("sql_customer_update"), customer.getCustomerName(), customer.getEmail(), customer.getCountry(), customer.getCustomerId());
        } catch (SQLException e) {
            throw new AccountException("Exception occurred while updating customer data", e);
        }
    }

    @Override
    public int deleteCustomer(final long customerId) throws AccountException {
        try (Connection connection = H2RepositoryFactory.getConnection()) {
            return new QueryRunner()
                    .update(connection, Utils.getStringProperty("sql_customer_delete_by_id"), customerId);
        } catch (SQLException e) {
            throw new AccountException("Exception occurred while deleting Customer Id:" + customerId, e);
        }
    }
}
