package com.org.transfers.resource;

import com.org.transfers.domain.MoneyTransfer;
import com.org.transfers.exception.AccountException;
import com.org.transfers.exception.PaymentException;
import com.org.transfers.repository.RepositoryFactory;
import com.org.transfers.utils.Utils;
import com.tunyk.currencyconverter.api.CurrencyConverterException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The type Transfers.
 */
@Path("/transfers")
@Produces(MediaType.APPLICATION_JSON)
public class Transfers {

    private final RepositoryFactory repositoryFactory = RepositoryFactory.getRepositoryFactory(RepositoryFactory.H2);

    /**
     * Transfer fund response.
     *
     * @param moneyTransfer the money transfer
     * @return the response
     * @throws PaymentException           the payment exception
     * @throws AccountException           the account exception
     * @throws CurrencyConverterException the currency converter exception
     */
    @POST
    public Response transferFund(MoneyTransfer moneyTransfer) throws PaymentException, AccountException, CurrencyConverterException {

        String sourceCurrency = repositoryFactory.getAccountRepository().getAccountById(moneyTransfer.getSourceAccountId()).getCurrencyCode();
        String targetCurrency = repositoryFactory.getAccountRepository().getAccountById(moneyTransfer.getTargetAccountId()).getCurrencyCode();

        if (sourceCurrency.equalsIgnoreCase(targetCurrency)) {
            int updateCount = repositoryFactory.getAccountRepository().transferAccountBalance(moneyTransfer);
            if (updateCount == 2) {
                return Response.status(Response.Status.OK).build();
            } else {
                throw new WebApplicationException("Exception occurred while transferring fund", Response.Status.BAD_REQUEST);
            }

        } else {
            float exchangeRate = Utils.getExchangeRate(sourceCurrency, targetCurrency);
            moneyTransfer.setAmount(moneyTransfer.getAmount().multiply(new BigDecimal(exchangeRate)).setScale(4, RoundingMode.HALF_EVEN));
            int updateCount = repositoryFactory.getAccountRepository().transferAccountBalance(moneyTransfer);
            if (updateCount == 2) {
                return Response.status(Response.Status.OK).build();
            } else {
                throw new WebApplicationException("Exception occurred while transferring fund", Response.Status.BAD_REQUEST);
            }
        }

    }

}
