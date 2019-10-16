package com.org.transfers.resource;

import com.org.transfers.exception.AccountException;
import com.org.transfers.exception.ErrorResponse;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The type Service exception mapper.
 */
@Provider
public class ServiceExceptionMapper implements ExceptionMapper<AccountException> {
    private static Logger log = Logger.getLogger(ServiceExceptionMapper.class);

    /**
     * Instantiates a new Service exception mapper.
     */
    public ServiceExceptionMapper() {
    }

    public Response toResponse(AccountException daoException) {
        if (log.isDebugEnabled()) {
            log.debug("Mapping exception to Response");
        }
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(daoException.getMessage());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
    }

}
