package com.org.transfers.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.transfers.repository.RepositoryFactory;
import com.org.transfers.resource.Accounts;
import com.org.transfers.resource.Customers;
import com.org.transfers.resource.ServiceExceptionMapper;
import com.org.transfers.resource.Transfers;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * The type Test resource.
 */
public abstract class TestResource {
    /**
     * The constant server.
     */
    protected static Server server = null;
    /**
     * The constant connManager.
     */
    protected static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

    /**
     * The constant client.
     */
    protected static HttpClient client;
    /**
     * The constant h2RepositoryFactory.
     */
    protected static RepositoryFactory h2RepositoryFactory =
            RepositoryFactory.getRepositoryFactory(RepositoryFactory.H2);
    /**
     * The Mapper.
     */
    protected ObjectMapper mapper = new ObjectMapper();
    /**
     * The Builder.
     */
    protected URIBuilder builder = new URIBuilder().setScheme("http").setHost("localhost:8084");

    /**
     * Sets .
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void setup() throws Exception {
        h2RepositoryFactory.populateTestData();
        startServer();
        connManager.setDefaultMaxPerRoute(100);
        connManager.setMaxTotal(200);
        client = HttpClients.custom()
                .setConnectionManager(connManager)
                .setConnectionManagerShared(true)
                .build();

    }

    /**
     * Close client.
     *
     * @throws Exception the exception
     */
    @AfterClass
    public static void closeClient() throws Exception {
        HttpClientUtils.closeQuietly(client);
    }

    private static void startServer() throws Exception {
        if (server == null) {
            server = new Server(8084);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);
            ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
            servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                    Customers.class.getCanonicalName() + "," +
                            Accounts.class.getCanonicalName() + "," +
                            ServiceExceptionMapper.class.getCanonicalName() + "," +
                            Transfers.class.getCanonicalName());
            server.start();
        }
    }
}
