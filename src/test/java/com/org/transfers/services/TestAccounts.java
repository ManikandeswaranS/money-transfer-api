package com.org.transfers.services;

import com.org.transfers.domain.AccountDetails;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
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
 * The type Test accounts.
 */
public class TestAccounts extends TestResource {

    /**
     * Test success get account by customer name.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessGetAccountByCustomerName() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/accounts/2").build();

        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();

        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        AccountDetails account = mapper.readValue(jsonString, AccountDetails.class);
        assertTrue(account.getCustomerName().equals("test2"));
    }

    /**
     * Test success get all accounts.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessGetAllAccounts() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/accounts/list").build();

        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
    }

    /**
     * Test success get account balance.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessGetAccountBalance() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/accounts/1/balance").build();

        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);

        String balance = EntityUtils.toString(response.getEntity());
        BigDecimal res = new BigDecimal(balance).setScale(4, RoundingMode.HALF_EVEN);
        BigDecimal db = new BigDecimal(700).setScale(4, RoundingMode.HALF_EVEN);
        assertTrue(res.equals(db));
    }

    /**
     * Test success create account.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessCreateAccount() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/accounts/create").build();

        StringEntity entity = new StringEntity(mapper.writeValueAsString(new AccountDetails("test1", new BigDecimal(0), "USD")));

        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
    }

    /**
     * Test failure create account existing.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testFailureCreateAccountExisting() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/accounts/create").build();
        BigDecimal balance = new BigDecimal(1000).setScale(4, RoundingMode.HALF_EVEN);

        String jsonInString = mapper.writeValueAsString(new AccountDetails(1L, "Savings", true, "test10", balance, "INR", 2000));
        StringEntity entity = new StringEntity(jsonInString);

        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 500);

    }

    /**
     * Test success delete account.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessDeleteAccount() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/accounts/3").build();

        HttpDelete request = new HttpDelete(uri);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
    }

    /**
     * Test success delete account not found.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessDeleteAccountNotFound() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/accounts/500").build();

        HttpDelete request = new HttpDelete(uri);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 404);
    }
}
