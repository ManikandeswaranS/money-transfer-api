package com.org.transfers.repository;

import com.org.transfers.domain.AccountDetails;
import com.org.transfers.domain.MoneyTransfer;
import com.org.transfers.exception.AccountException;
import com.org.transfers.exception.PaymentException;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface Account repository.
 */
public interface AccountRepository {

    /**
     * Gets all accounts.
     *
     * @return the all accounts
     * @throws AccountException the account exception
     */
    List<AccountDetails> getAllAccounts() throws AccountException;

    /**
     * Gets account by id.
     *
     * @param accountId the account id
     * @return the account by id
     * @throws AccountException the account exception
     */
    AccountDetails getAccountById(long accountId) throws AccountException;

    /**
     * Create account long.
     *
     * @param account the account
     * @return the long
     * @throws AccountException the account exception
     */
    long createAccount(AccountDetails account) throws AccountException;

    /**
     * Delete account by id int.
     *
     * @param accountId the account id
     * @return the int
     * @throws AccountException the account exception
     */
    int deleteAccountById(long accountId) throws AccountException;

    /**
     * Update account balance int.
     *
     * @param accountId   the account id
     * @param deltaAmount the delta amount
     * @return the int
     * @throws PaymentException the payment exception
     */
    int updateAccountBalance(long accountId, BigDecimal deltaAmount) throws PaymentException;

    /**
     * Transfer account balance int.
     *
     * @param moneyTransfer the money transfer
     * @return the int
     * @throws PaymentException the payment exception
     * @throws AccountException the account exception
     */
    int transferAccountBalance(MoneyTransfer moneyTransfer) throws PaymentException, AccountException;
}