package com.org.transfers.resource;

import com.org.transfers.domain.AccountDetails;
import com.org.transfers.exception.AccountException;
import com.org.transfers.exception.PaymentException;
import com.org.transfers.repository.RepositoryFactory;
import com.org.transfers.utils.Utils;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * The type Accounts.
 */
@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class Accounts {

    private final RepositoryFactory repositoryFactory = RepositoryFactory.getRepositoryFactory(RepositoryFactory.H2);

    private static Logger log = Logger.getLogger(Accounts.class);

    /**
     * Gets all accounts.
     *
     * @return the all accounts
     * @throws AccountException the account exception
     */
    @GET
    @Path("/list")
    public List<AccountDetails> getAllAccounts() throws AccountException {
        return repositoryFactory.getAccountRepository().getAllAccounts();
    }

    /**
     * Gets account.
     *
     * @param accountId the account id
     * @return the account
     * @throws AccountException the account exception
     */
    @GET
    @Path("/{accountId}")
    public AccountDetails getAccount(@PathParam("accountId") long accountId) throws AccountException {
        return repositoryFactory.getAccountRepository().getAccountById(accountId);
    }

    /**
     * Gets balance.
     *
     * @param accountId the account id
     * @return the balance
     * @throws AccountException the account exception
     */
    @GET
    @Path("/{accountId}/balance")
    public BigDecimal getBalance(@PathParam("accountId") long accountId) throws AccountException {
        final AccountDetails account = repositoryFactory.getAccountRepository().getAccountById(accountId);

        if (account == null) {
            throw new WebApplicationException("Account not found", Response.Status.NOT_FOUND);
        }
        return account.getAccountBalance();
    }

    /**
     * Create account account details.
     *
     * @param account the account
     * @return the account details
     * @throws AccountException the account exception
     */
    @PUT
    @Path("/create")
    public AccountDetails createAccount(AccountDetails account) throws AccountException {

        if (Utils.validateCustomer(repositoryFactory.getCustomerRepository().getCustomerByName(account.getCustomerName()))) {
            final long accountId = repositoryFactory.getAccountRepository().createAccount(account);
            return repositoryFactory.getAccountRepository().getAccountById(accountId);
        } else {
            throw new AccountException("Cannot create account for this customer");
        }
    }

    /**
     * Deposit account details.
     *
     * @param accountId the account id
     * @param amount    the amount
     * @return the account details
     * @throws AccountException the account exception
     * @throws PaymentException the payment exception
     */
    @PUT
    @Path("/{accountId}/deposit/{amount}")
    public AccountDetails deposit(@PathParam("accountId") long accountId, @PathParam("amount") BigDecimal amount) throws AccountException, PaymentException {

        if (amount.compareTo(Utils.zeroAmount) <= 0) {
            throw new WebApplicationException("Invalid amount", Response.Status.BAD_REQUEST);
        } else {
            repositoryFactory.getAccountRepository().updateAccountBalance(accountId, amount.setScale(4, RoundingMode.HALF_EVEN));
            return repositoryFactory.getAccountRepository().getAccountById(accountId);
        }
    }

    /**
     * Withdraw account details.
     *
     * @param accountId the account id
     * @param amount    the amount
     * @return the account details
     * @throws AccountException the account exception
     * @throws PaymentException the payment exception
     */
    @PUT
    @Path("/{accountId}/withdraw/{amount}")
    public AccountDetails withdraw(@PathParam("accountId") long accountId, @PathParam("amount") BigDecimal amount) throws AccountException, PaymentException {

        int withdrawLimit = repositoryFactory.getAccountRepository().getAccountById(accountId).getWithDrawalLimit();
        if (amount.compareTo(Utils.zeroAmount) <= 0) {
            throw new WebApplicationException("Invalid amount", Response.Status.BAD_REQUEST);
        } else if (amount.compareTo(new BigDecimal(withdrawLimit)) == 1) {
            throw new WebApplicationException("Withdraw amount is more than the allowed limit for this account : " + accountId);
        }
        BigDecimal withdrawAmount = amount.negate();
        if (log.isDebugEnabled())
            log.debug("Withdraw amount " + amount + " from account id : " + accountId);
        repositoryFactory.getAccountRepository().updateAccountBalance(accountId, withdrawAmount.setScale(4, RoundingMode.HALF_EVEN));
        return repositoryFactory.getAccountRepository().getAccountById(accountId);
    }


    /**
     * Delete account response.
     *
     * @param accountId the account id
     * @return the response
     * @throws AccountException the account exception
     */
    @DELETE
    @Path("/{accountId}")
    public Response deleteAccount(@PathParam("accountId") long accountId) throws AccountException {
        int deleteCount = repositoryFactory.getAccountRepository().deleteAccountById(accountId);
        if (deleteCount == 1) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
