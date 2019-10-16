package com.org.transfers.repository;

import com.org.transfers.domain.CustomerDetails;
import com.org.transfers.exception.AccountException;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * The type Test customer repository.
 */
public class TestCustomerRepository {

    private static Logger log = Logger.getLogger(TestCustomerRepository.class);

    private static final RepositoryFactory repositoryFactory = RepositoryFactory.getRepositoryFactory(RepositoryFactory.H2);

    /**
     * Sets up H2 database.
     */
    @BeforeClass
    public static void setup() {
        repositoryFactory.populateTestData();
    }

    /**
     * Test success get all customer.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessGetAllCustomer() throws AccountException {
        assertTrue(repositoryFactory.getCustomerRepository().getAllCustomers().size() > 1);
    }

    /**
     * Test success get customer by id.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessGetCustomerById() throws AccountException {
        assertTrue(repositoryFactory.getCustomerRepository().getCustomerById(2L).getCustomerName().equals("test2"));
    }

    /**
     * Test failure get customer by id not found.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testFailureGetCustomerByIdNotFound() throws AccountException {
        assertTrue(repositoryFactory.getCustomerRepository().getCustomerById(500L) == null);
    }

    /**
     * Test success get customer by name.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessGetCustomerByName() throws AccountException {
        assertTrue(repositoryFactory.getCustomerRepository().getCustomerByName("test2").getEmail().equals("test2@test.com"));
    }

    /**
     * Test failure get customer by name not found.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testFailureGetCustomerByNameNotFound() throws AccountException {
        assertTrue(repositoryFactory.getCustomerRepository().getCustomerByName("12121") == null);
    }

    /**
     * Test success create customer.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessCreateCustomer() throws AccountException {
        String customerName = repositoryFactory.getCustomerRepository().insertCustomer(new CustomerDetails("test11", "test11@test.com", "UK"));
        CustomerDetails createdCustomer = repositoryFactory.getCustomerRepository().getCustomerByName(customerName);
        assertTrue(createdCustomer.getCustomerName().equals("test11"));
        assertTrue(createdCustomer.getEmail().equals("test11@test.com"));
        assertTrue(createdCustomer.getCountry().equals("UK"));
    }

    /**
     * Test success update customer.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessUpdateCustomer() throws AccountException {
        int rowCount = repositoryFactory.getCustomerRepository().updateCustomer(1L, new CustomerDetails(1L, "test1", "test1@gmail.com", "UK"));
        assertTrue(rowCount == 1);
        assertTrue(repositoryFactory.getCustomerRepository().getCustomerById(1L).getEmail().equals("test1@gmail.com"));
    }

    /**
     * Test failure update customer not found.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testFailureUpdateCustomerNotFound() throws AccountException {
        assertTrue(repositoryFactory.getCustomerRepository().updateCustomer(500L, new CustomerDetails(500L, "test2", "test2@gmail.com", "USA")) == 0);
    }

    /**
     * Test success delete customer.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessDeleteCustomer() throws AccountException {
        assertTrue(repositoryFactory.getCustomerRepository().deleteCustomer(1L) == 1);
        assertTrue(repositoryFactory.getCustomerRepository().getCustomerById(1L) == null);
    }

    /**
     * Test failure delete customer not found.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testFailureDeleteCustomerNotFound() throws AccountException {
        assertTrue(repositoryFactory.getCustomerRepository().deleteCustomer(500L) == 0);
    }

}
