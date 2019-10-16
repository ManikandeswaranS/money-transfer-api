package com.org.transfers.services;


import com.org.transfers.domain.MoneyTransfer;
import com.org.transfers.exception.AccountException;
import com.org.transfers.repository.AccountRepository;
import com.org.transfers.repository.RepositoryFactory;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertTrue;

/**
 * The type Test transfers concurrency.
 */
public class TestTransfersConcurrency extends TestResource {

    private static final RepositoryFactory h2RepositoryFactory = RepositoryFactory.getRepositoryFactory(RepositoryFactory.H2);
    private static final int THREADS_COUNT = 1000;

    /**
     * Test transfer multi threaded.
     *
     * @throws URISyntaxException   the uri syntax exception
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     * @throws AccountException     the account exception
     */
    @Test
    public void testTransferMultiThreaded() throws URISyntaxException, IOException, InterruptedException, AccountException {

        final AccountRepository accountRepository = h2RepositoryFactory.getAccountRepository();

        BigDecimal totalBalancBeforeTransfer = new BigDecimal(String.valueOf(accountRepository.getAccountById(1).getAccountBalance()))
                .add(new BigDecimal(String.valueOf(accountRepository.getAccountById(2).getAccountBalance())));

        final CountDownLatch latch = new CountDownLatch(THREADS_COUNT);
        for (int i = 0; i < THREADS_COUNT; i++) {
            new Thread(() -> {
                try {
                    MoneyTransfer moneyTransfer = new MoneyTransfer(new BigDecimal(20).setScale(4, RoundingMode.HALF_EVEN), 1L, 2L);
                    accountRepository.transferAccountBalance(moneyTransfer);
                } catch (Exception e) {
                    e.printStackTrace(); // printing stack trace as it is test case.
                } finally {
                    latch.countDown();
                }
            }).start();
        }
        latch.await();

        assertTrue(totalBalancBeforeTransfer.equals(new BigDecimal(String.valueOf(accountRepository.getAccountById(1).getAccountBalance()))
                .add(new BigDecimal(String.valueOf(accountRepository.getAccountById(2).getAccountBalance())))));

    }
}