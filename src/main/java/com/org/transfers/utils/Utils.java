package com.org.transfers.utils;

import com.org.transfers.domain.CustomerDetails;
import com.org.transfers.exception.PaymentException;
import com.tunyk.currencyconverter.BankUaCom;
import com.tunyk.currencyconverter.api.Currency;
import com.tunyk.currencyconverter.api.CurrencyConverter;
import com.tunyk.currencyconverter.api.CurrencyConverterException;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * The type Utils.
 */
public class Utils {

    private static Properties properties = new Properties();

    private static List<String> sanctionedCountries = new ArrayList<String>(Arrays.asList("XYZ", "ABC"));

    /**
     * The constant zeroAmount.
     */
    public static final BigDecimal zeroAmount = new BigDecimal(0).setScale(4, RoundingMode.HALF_EVEN);

    /**
     * The Log.
     */
    static Logger log = Logger.getLogger(Utils.class);

    /**
     * Load config.
     *
     * @param fileName the file name
     */
    public static void loadConfig(String fileName) {
        if (fileName == null) {
            log.warn("loadConfig: config file name cannot be null");
        } else {
            try {
                log.info("loadConfig(): Loading config file: " + fileName);
                final InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
                properties.load(fis);

            } catch (FileNotFoundException fne) {
                log.error("loadConfig(): file name not found " + fileName, fne);
            } catch (IOException ioe) {
                log.error("loadConfig(): error when reading the config " + fileName, ioe);
            }
        }

    }

    /**
     * Gets string property.
     *
     * @param key the key
     * @return the string property
     */
    public static String getStringProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            value = System.getProperty(key);
        }
        return value;
    }

    //initialise

    static {
        String configFileName = System.getProperty("application.properties");

        if (configFileName == null) {
            configFileName = "application.properties";
        }
        loadConfig(configFileName);

    }

    /**
     * Validate customer boolean.
     *
     * @param customer the customer
     * @return the boolean
     */
    public static boolean validateCustomer(CustomerDetails customer) {
        if (sanctionedCountries.contains(customer.getCountry())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets exchange rate.
     *
     * @param sourceCurrency the source currency
     * @param targetCurrency the target currency
     * @return the exchange rate
     * @throws PaymentException           the payment exception
     * @throws CurrencyConverterException the currency converter exception
     */
    public static float getExchangeRate(String sourceCurrency, String targetCurrency) throws PaymentException, CurrencyConverterException {
        CurrencyConverter currencyConverter = new BankUaCom(Currency.fromString(sourceCurrency), Currency.fromString(targetCurrency));
        return currencyConverter.convertCurrency(1f);
    }

}
