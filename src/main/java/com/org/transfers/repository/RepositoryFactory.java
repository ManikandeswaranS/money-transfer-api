package com.org.transfers.repository;

/**
 * The type Repository factory.
 */
public abstract class RepositoryFactory {

    /**
     * The constant H2.
     */
    public static final int H2 = 1;

    /**
     * Gets customer repository.
     *
     * @return the customer repository
     */
    public abstract CustomerRepository getCustomerRepository();

    /**
     * Gets account repository.
     *
     * @return the account repository
     */
    public abstract AccountRepository getAccountRepository();

    /**
     * Populate test data.
     */
    public abstract void populateTestData();

    /**
     * Gets repository factory.
     *
     * @param factoryCode the factory code
     * @return the repository factory
     */
    public static RepositoryFactory getRepositoryFactory(int factoryCode) {

        switch (factoryCode) {
            case H2:
                return new H2RepositoryFactory();
            default:
                // by default using H2 in-memory database
                return new H2RepositoryFactory();
        }
    }
}
