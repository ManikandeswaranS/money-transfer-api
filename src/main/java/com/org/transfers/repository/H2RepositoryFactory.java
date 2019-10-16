package com.org.transfers.repository;

import com.org.transfers.repository.impl.AccountRepositoryImpl;
import com.org.transfers.repository.impl.CustomerRepositoryImpl;
import com.org.transfers.utils.Utils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type H 2 repository factory.
 */
public class H2RepositoryFactory extends RepositoryFactory {
    private static final String h2_driver = Utils.getStringProperty("h2_driver");
    private static final String h2_connection_url = Utils.getStringProperty("h2_connection_url");
    private static final String h2_user = Utils.getStringProperty("h2_user");
    private static final String h2_password = Utils.getStringProperty("h2_password");
    private static Logger log = Logger.getLogger(H2RepositoryFactory.class);

    private final CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
    private final AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();

    /**
     * Instantiates a new H 2 repository factory.
     */
    H2RepositoryFactory() {
        DbUtils.loadDriver(h2_driver);
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(h2_connection_url, h2_user, h2_password);

    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    @Override
    public void populateTestData() {
        log.info("Populating h2 database with test data");
        Connection conn = null;
        try {
            conn = H2RepositoryFactory.getConnection();
            RunScript.execute(conn, new FileReader("src/test/resources/test_data.sql"));
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while populating user data", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Exception occurred while finding test script file", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

}
