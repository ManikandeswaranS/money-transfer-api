package com.org.transfers;

import com.org.transfers.repository.RepositoryFactory;
import com.org.transfers.resource.Accounts;
import com.org.transfers.resource.Customers;
import com.org.transfers.resource.ServiceExceptionMapper;
import com.org.transfers.resource.Transfers;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * The type Application.
 */
public class Application {

    private static Logger log = Logger.getLogger(Application.class);

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        log.info("Initialize h2 database");
        RepositoryFactory h2DaoFactory = RepositoryFactory.getRepositoryFactory(RepositoryFactory.H2);
        h2DaoFactory.populateTestData();
        log.info("Initialized h2 database successfully");

        // Host service on jetty
        startService();
    }

    private static void startService() throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                Customers.class.getCanonicalName() + "," + Accounts.class.getCanonicalName() + ","
                        + ServiceExceptionMapper.class.getCanonicalName() + ","
                        + Transfers.class.getCanonicalName());
        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }
}
