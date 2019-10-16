package com.org.transfers.services;

import com.org.transfers.domain.CustomerDetails;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

/**
 * The type Test customers.
 */
public class TestCustomers extends TestResource {

    /**
     * Test success get all customers.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessGetAllCustomers() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/customers/list").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);

        String jsonString = EntityUtils.toString(response.getEntity());
        assertTrue(mapper.readValue(jsonString, CustomerDetails[].class).length > 0);
    }

    /**
     * Test success get customer.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessGetCustomer() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/customers/test1").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();

        assertTrue(statusCode == 200);

        String jsonString = EntityUtils.toString(response.getEntity());
        CustomerDetails customerDetails = mapper.readValue(jsonString, CustomerDetails.class);
        assertTrue(customerDetails.getCustomerName().equals("test1"));
        assertTrue(customerDetails.getEmail().equals("test1@test.com"));

    }

    /**
     * Test success create customer.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessCreateCustomer() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/customers/create").build();

        StringEntity entity = new StringEntity(mapper.writeValueAsString(new CustomerDetails("test12", "test12@test.com", "India")));
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);

        String jsonString = EntityUtils.toString(response.getEntity());
        CustomerDetails createdCustomer = mapper.readValue(jsonString, CustomerDetails.class);
        assertTrue(createdCustomer.getCustomerName().equals("test12"));
        assertTrue(createdCustomer.getEmail().equals("test12@test.com"));
        assertTrue(createdCustomer.getCountry().equals("India"));
    }

    /**
     * Test failure create customer existing customer.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testFailureCreateCustomerExistingCustomer() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/customers/create").build();

        StringEntity entity = new StringEntity(mapper.writeValueAsString(new CustomerDetails("test1", "test1@gmail.com", "UK")));
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 400);

    }

    /**
     * Test success update customer.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessUpdateCustomer() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/customers/2").build();

        StringEntity entity = new StringEntity(mapper.writeValueAsString(new CustomerDetails(2L, "test2", "test2@gmail.com", "India")));
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
    }

    /**
     * Test failure update customer not found.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testFailureUpdateCustomerNotFound() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/customers/500").build();

        StringEntity entity = new StringEntity(mapper.writeValueAsString(new CustomerDetails(500L, "test1", "test1123@gmail.com", "India")));
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 404);
    }

    /**
     * Test success delete customer.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testSuccessDeleteCustomer() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/customers/3").build();

        HttpDelete request = new HttpDelete(uri);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
    }

    /**
     * Test failure delete customer not found.
     *
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    public void testFailureDeleteCustomerNotFound() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/customers/1100").build();

        HttpDelete request = new HttpDelete(uri);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 404);
    }

}