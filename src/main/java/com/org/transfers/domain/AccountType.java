package com.org.transfers.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * The enum Account type.
 */
public enum AccountType {
    /**
     * Current account type.
     */
    CURRENT("current", 1000, 500),
    /**
     * Savings account type.
     */
    SAVINGS("savings", 500, 250);

    private static final Map<String, AccountType> BY_LABEL = new HashMap<>();
    private static final Map<Integer, AccountType> BY_TRANSFER_LIMIT = new HashMap<>();
    private static final Map<Integer, AccountType> BY_WITHDRAW_LIMIT = new HashMap<>();

    static {
        for (AccountType e : values()) {
            BY_LABEL.put(e.label, e);
            BY_TRANSFER_LIMIT.put(e.transferLimit, e);
            BY_WITHDRAW_LIMIT.put(e.withdrawLimit, e);
        }
    }

    /**
     * The Label.
     */
    public final String label;
    /**
     * The Transfer limit.
     */
    public final int transferLimit;
    /**
     * The Withdraw limit.
     */
    public final int withdrawLimit;

    private AccountType(String label, int transferLimit, int withdrawLimit) {
        this.label = label;
        this.transferLimit = transferLimit;
        this.withdrawLimit = withdrawLimit;
    }

    /**
     * Value of label account type.
     *
     * @param label the label
     * @return the account type
     */
    public static AccountType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    /**
     * Value of transfer limit account type.
     *
     * @param number the number
     * @return the account type
     */
    public static AccountType valueOfTransferLimit(int number) {
        return BY_TRANSFER_LIMIT.get(number);
    }

    /**
     * Value of withdraw limit account type.
     *
     * @param number the number
     * @return the account type
     */
    public static AccountType valueOfWithdrawLimit(int number) {
        return BY_WITHDRAW_LIMIT.get(number);
    }

}
