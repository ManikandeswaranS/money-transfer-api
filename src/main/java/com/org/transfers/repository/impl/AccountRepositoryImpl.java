package com.org.transfers.repository.impl;

import com.org.transfers.domain.AccountDetails;
import com.org.transfers.domain.MoneyTransfer;
import com.org.transfers.exception.AccountException;
import com.org.transfers.exception.PaymentException;
import com.org.transfers.repository.AccountRepository;
import com.org.transfers.repository.H2RepositoryFactory;
import com.org.transfers.utils.Utils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Account repository.
 */
public class AccountRepositoryImpl implements AccountRepository {

    private static Logger log = Logger.getLogger(AccountRepositoryImpl.class);

    private Object lock = new Object();

    public List<AccountDetails> getAllAccounts() throws AccountException {
        try (Connection connection = H2RepositoryFactory.getConnection()) {
            return new QueryRunner()
                    .query(connection, Utils.getStringProperty("sql_all_accounts"), new BeanListHandler<>(AccountDetails.class));
        } catch (SQLException e) {
            throw new AccountException("Exception occurred while reading all account details", e);
        }
    }

    @Override
    public AccountDetails getAccountById(final long accountId) throws AccountException {
        try (Connection connection = H2RepositoryFactory.getConnection()) {
            AccountDetails accountDetails = new QueryRunner()
                    .query(connection, Utils.getStringProperty("sql_account_by_id"), new BeanHandler<>(AccountDetails.class), accountId);
            if (log.isDebugEnabled()) {
                log.debug("Retrieved Account : " + accountDetails);

            }
            return accountDetails;
        } catch (SQLException e) {
            throw new AccountException("Exception occurred while reading account data", e);
        }
    }

    @Override
    public long createAccount(final AccountDetails account) throws AccountException {
        try (Connection connection = H2RepositoryFactory.getConnection()) {
            String isValid = null;
            if (account.isAccountValid()) {
                isValid = "valid";
            } else {
                isValid = "invalid";
            }
            return new QueryRunner()
                    .update(connection, Utils.getStringProperty("sql_account_creation"), account.getCustomerName(), account.getAccountBalance(), account.getCurrencyCode(), account.getAccountType(), isValid, 1000);
        } catch (SQLException e) {
            throw new AccountException("Exception occurred while creating customer account " + account, e);
        }
    }

    @Override
    public int deleteAccountById(final long accountId) throws AccountException {
        try (Connection connection = H2RepositoryFactory.getConnection()) {
            return new QueryRunner()
                    .update(connection, Utils.getStringProperty("sql_account_delete_by_id"), accountId);
        } catch (SQLException e) {
            throw new AccountException("Exception occurred while reading account data", e);
        }
    }

    @Override
    public int updateAccountBalance(final long accountId, final BigDecimal updateAmount) throws PaymentException {
        Connection connection = null;
        AccountDetails targetAccount = null;
        int updateCount = -1;
        synchronized (lock) {
            try {
                connection = H2RepositoryFactory.getConnection();
                connection.setAutoCommit(false);
                targetAccount = new QueryRunner()
                        .query(connection, Utils.getStringProperty("sql_account_by_id"), new BeanHandler<>(AccountDetails.class), accountId);
                if (log.isDebugEnabled()) {
                    log.debug("updateAccountBalance from Account: " + targetAccount);
                }
                if (targetAccount == null) {
                    throw new PaymentException("Unable to get target account details" + accountId);
                }
                BigDecimal balance = targetAccount.getAccountBalance().add(updateAmount);
                if (balance.compareTo(Utils.zeroAmount) < 0) {
                    throw new PaymentException("Insufficient Fund in account: " + accountId);
                }

                updateCount = new QueryRunner()
                        .update(connection, Utils.getStringProperty("sql_account_balance_update"), balance, accountId);
                connection.commit();
                if (log.isDebugEnabled()) {
                    log.debug("New Balance after Update: " + targetAccount);
                }
                return updateCount;
            } catch (SQLException se) {
                //rollback transaction for exception
                log.error("Customer Transaction Failed, rollback initiated for: " + accountId, se);
                try {
                    if (connection != null) {
                        connection.rollback();
                    }
                } catch (SQLException re) {
                    throw new PaymentException("Failed to rollback transaction", re);
                }
            } finally {
                DbUtils.closeQuietly(connection);
            }
        }
        return updateCount;
    }

    @Override
    public int transferAccountBalance(final MoneyTransfer moneyTransfer) throws PaymentException, AccountException {
        int result = -1;
        Connection connection = null;
        AccountDetails sourceAccount = null;
        AccountDetails targetAccount = null;

        synchronized (lock) {
            try {
                connection = H2RepositoryFactory.getConnection();
                connection.setAutoCommit(false);

                sourceAccount = new QueryRunner()
                        .query(connection, Utils.getStringProperty("sql_account_by_id"), new BeanHandler<>(AccountDetails.class), moneyTransfer.getSourceAccountId());
                if (log.isDebugEnabled()) {
                    log.debug("transferAccountBalance from Account: " + sourceAccount);
                }

                targetAccount = new QueryRunner()
                        .query(connection, Utils.getStringProperty("sql_account_by_id"), new BeanHandler<>(AccountDetails.class), moneyTransfer.getTargetAccountId());
                if (log.isDebugEnabled()) {
                    log.debug("transferAccountBalance from Account: " + targetAccount);
                }

                if (sourceAccount == null || targetAccount == null) {
                    throw new PaymentException("Unable to retrieve both source and target accounts successfully");
                }

                BigDecimal accountFund = sourceAccount.getAccountBalance().subtract(moneyTransfer.getAmount());
                if (accountFund.compareTo(Utils.zeroAmount) < 0) {
                    throw new PaymentException("Source account has zero balance");
                }

                QueryRunner queryRunner = new QueryRunner();
                Integer rowUpdatedSource = queryRunner.update(connection, Utils.getStringProperty("sql_account_balance_update"), accountFund, moneyTransfer.getSourceAccountId());
                Integer rowUpdatedTarget = queryRunner.update(connection, Utils.getStringProperty("sql_account_balance_update"), targetAccount.getAccountBalance().add(moneyTransfer.getAmount()), moneyTransfer.getTargetAccountId());

                result = rowUpdatedSource + rowUpdatedTarget;
                if (log.isDebugEnabled()) {
                    log.debug("Number of rows updated for the transfer : " + result);
                }
                connection.commit();
                String threadName = Thread.currentThread().getName();
                log.debug(threadName + " transferred " + moneyTransfer.getAmount() + " from "
                        + moneyTransfer.getSourceAccountId() + " to " + moneyTransfer.getTargetAccountId()
                        + ". Total balance is " + (sourceAccount.getAccountBalance().add(targetAccount.getAccountBalance())));
            } catch (SQLException se) {
                log.error("Customer Transaction Failed, rollback initiated for: " + moneyTransfer,
                        se);
                try {
                    if (connection != null)
                        connection.rollback();
                } catch (SQLException re) {
                    throw new PaymentException("Fail to rollback transaction", re);
                }
            } finally {
                DbUtils.closeQuietly(connection);
            }
        }
        return result;
    }
}
