package com.org.transfers.services;


import com.org.transfers.domain.AccountDetails;
import com.org.transfers.domain.MoneyTransfer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

/**
 * The type Test transfers.
 */
public class TestTransfers extends TestResource {

    /**
     * Test success deposit.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessDeposit() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/accounts/1/deposit/100").build();

        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);

    }

    /**
     * Test success with draw sufficient fund.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessWithDrawSufficientFund() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/accounts/1/withdraw/10").build();

        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);

        assertTrue(mapper.readValue(EntityUtils.toString(response.getEntity()), AccountDetails.class).getAccountBalance()
                .equals(new BigDecimal(100).setScale(4, RoundingMode.HALF_EVEN)));

    }

    /**
     * Test failure with draw in sufficient fund.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testFailureWithDrawInSufficientFund() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/accounts/2/withdraw/1000").build();

        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 500);
    }

    /**
     * Test success transfer fund sufficient fund.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessTransferFundSufficientFund() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/transfers").build();
        BigDecimal amount = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);

        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(mapper.writeValueAsString(new MoneyTransfer(amount, 1L, 4L))));
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
    }

    /**
     * Test success transfer fund insufficient fund.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessTransferFundInsufficientFund() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/transfers").build();
        BigDecimal amount = new BigDecimal(100000).setScale(4, RoundingMode.HALF_EVEN);

        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(mapper.writeValueAsString(new MoneyTransfer(amount, 1L, 4L))));
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 500);
    }

    /**
     * Test success transfer fund different ccy.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessTransferFundDifferentCcy() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/transfers").build();
        BigDecimal amount = new BigDecimal(100).setScale(4, RoundingMode.HALF_EVEN);

        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(mapper.writeValueAsString(new MoneyTransfer(amount, 1L, 2L))));
        HttpResponse response = client.execute(request);

        assertTrue(response.getStatusLine().getStatusCode() == 200);
    }
}
