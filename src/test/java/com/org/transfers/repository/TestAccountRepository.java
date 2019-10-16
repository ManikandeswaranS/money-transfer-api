package com.org.transfers.repository;

import com.org.transfers.domain.AccountDetails;
import com.org.transfers.exception.AccountException;
import com.org.transfers.exception.PaymentException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static junit.framework.TestCase.*;

/**
 * The type Test account repository.
 */
public class TestAccountRepository {

    private static final RepositoryFactory repositoryFactory = RepositoryFactory.getRepositoryFactory(RepositoryFactory.H2);

    /**
     * Sets up H2 database.
     */
    @BeforeClass
    public static void setup() {
        repositoryFactory.populateTestData();
    }

    /**
     * Test success get all accounts.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessGetAllAccounts() throws AccountException {
        assertTrue(repositoryFactory.getAccountRepository().getAllAccounts().size() > 1);
    }

    /**
     * Test success get account by id.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessGetAccountById() throws AccountException {
        assertTrue(repositoryFactory.getAccountRepository().getAccountById(1L).getCustomerName().equals("test1"));
    }

    /**
     * Test failure get account by id not found.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testFailureGetAccountByIdNotFound() throws AccountException {
        assertNull(repositoryFactory.getAccountRepository().getAccountById(100L));
    }

    /**
     * Test success create account.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessCreateAccount() throws AccountException {
        BigDecimal balance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
        AccountDetails account = new AccountDetails(10, "Current", true, "test10", balance, "USD", 1000);
        assertEquals(repositoryFactory.getAccountRepository().createAccount(account), 1);
    }

    /**
     * Test success delete account.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessDeleteAccount() throws AccountException {
        assertTrue(repositoryFactory.getAccountRepository().deleteAccountById(1L) == 1);
    }

    /**
     * Test failure delete unknown account.
     *
     * @throws AccountException the account exception
     */
    @Test
    public void testFailureDeleteUnknownAccount() throws AccountException {
        int rowCount = repositoryFactory.getAccountRepository().deleteAccountById(900L);
        assertTrue(rowCount == 0);
    }

    /**
     * Test success update account balance sufficient fund.
     *
     * @throws PaymentException the payment exception
     * @throws AccountException the account exception
     */
    @Test
    public void testSuccessUpdateAccountBalanceSufficientFund() throws PaymentException, AccountException {

        BigDecimal currentBalance = repositoryFactory.getAccountRepository().getAccountById(1L).getAccountBalance();
        assertTrue(repositoryFactory.getAccountRepository().updateAccountBalance(1L, new BigDecimal(50).setScale(4, RoundingMode.HALF_EVEN)) == 1);
        assertTrue(repositoryFactory.getAccountRepository().getAccountById(1L).getAccountBalance().equals(currentBalance.add(new BigDecimal(50).setScale(4, RoundingMode.HALF_EVEN))));

    }

    /**
     * Test failure update account balance in sufficient fund.
     *
     * @throws AccountException the account exception
     * @throws PaymentException the payment exception
     */
    @Test(expected = PaymentException.class)
    public void testFailureUpdateAccountBalanceInSufficientFund() throws AccountException, PaymentException {
        BigDecimal withDrawAmount = new BigDecimal(-50000).setScale(4, RoundingMode.HALF_EVEN);
        assertTrue(repositoryFactory.getAccountRepository().updateAccountBalance(1L, withDrawAmount) == 0);
    }

}