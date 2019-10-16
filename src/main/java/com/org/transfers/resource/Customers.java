package com.org.transfers.resource;

import com.org.transfers.domain.CustomerDetails;
import com.org.transfers.exception.AccountException;
import com.org.transfers.repository.RepositoryFactory;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The type Customers.
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class Customers {

    private final RepositoryFactory repositoryFactory = RepositoryFactory.getRepositoryFactory(RepositoryFactory.H2);

    private static Logger log = Logger.getLogger(Customers.class);

    /**
     * Gets customer by name.
     *
     * @param customerName the customer name
     * @return the customer by name
     * @throws AccountException the account exception
     */
    @GET
    @Path("/{customerName}")
    public CustomerDetails getCustomerByName(@PathParam("customerName") String customerName) throws AccountException {
        if (log.isDebugEnabled())
            log.debug("Request Received for get customer by Name " + customerName);
        final CustomerDetails customerDetails = repositoryFactory.getCustomerRepository().getCustomerByName(customerName);
        if (customerDetails == null) {
            throw new WebApplicationException("Customer Not Found", Response.Status.NOT_FOUND);
        }
        return customerDetails;
    }

    /**
     * Gets all customer.
     *
     * @return the all customer
     * @throws AccountException the account exception
     */
    @GET
    @Path("/list")
    public List<CustomerDetails> getAllCustomer() throws AccountException {
        return repositoryFactory.getCustomerRepository().getAllCustomers();
    }

    /**
     * Create customer customer details.
     *
     * @param customerDetails the customer details
     * @return the customer details
     * @throws AccountException the account exception
     */
    @POST
    @Path("/create")
    public CustomerDetails createCustomer(CustomerDetails customerDetails) throws AccountException {
        if (repositoryFactory.getCustomerRepository().getCustomerByName(customerDetails.getCustomerName()) != null) {
            throw new WebApplicationException("Customer name already exist", Response.Status.BAD_REQUEST);
        }
        final String customerName = repositoryFactory.getCustomerRepository().insertCustomer(customerDetails);
        return repositoryFactory.getCustomerRepository().getCustomerByName(customerName);
    }

    /**
     * Update customer response.
     *
     * @param customerId      the customer id
     * @param customerDetails the customer details
     * @return the response
     * @throws AccountException the account exception
     */
    @PUT
    @Path("/{customerId}")
    public Response updateCustomer(@PathParam("customerId") long customerId, CustomerDetails customerDetails) throws AccountException {
        final int updateCount = repositoryFactory.getCustomerRepository().updateCustomer(customerId, customerDetails);
        if (updateCount == 1) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Delete customer response.
     *
     * @param customerId the customer id
     * @return the response
     * @throws AccountException the account exception
     */
    @DELETE
    @Path("/{customerId}")
    public Response deleteCustomer(@PathParam("customerId") long customerId) throws AccountException {
        int deleteCount = repositoryFactory.getCustomerRepository().deleteCustomer(customerId);
        if (deleteCount == 1) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
