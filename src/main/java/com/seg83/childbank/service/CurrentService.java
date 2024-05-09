package com.seg83.childbank.service;

import com.seg83.childbank.dao.CurrentAccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The CurrentService class provides functionality related to managing the current account.
 */
@Service
@Slf4j
public class CurrentService {
    private final CurrentAccountDao currentAccountDao;

    /**
     * Constructs a new CurrentService with the specified CurrentAccountDao.
     *
     * @param currentAccountDao The data access object for the current account.
     */
    @Autowired
    public CurrentService(CurrentAccountDao currentAccountDao) {
        this.currentAccountDao = currentAccountDao;
    }

    /**
     * Checks the balance of the current account.
     *
     * @return The balance of the current account.
     */
    public Double checkCurrentAccountBalance() {
        log.info("Checking Current Account Balance");
        return (Double) currentAccountDao.getAttribute("currentAccountAmount");
    }

    // TODO: Overflow detection
    /**
     * Deposits an amount into the current account.
     *
     * @param amount The amount to deposit.
     */
    public void depositCurrentAccount(int amount) {
        double currentAmount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        double newAmount = currentAmount + amount;
        currentAccountDao.setAttribute("currentAccountAmount", newAmount);
        log.info("Deposit current {} now {} -> {}", amount, currentAmount, newAmount);
    }

    // TODO: Overflow detection
    /**
     * Withdraws an amount from the current account.
     *
     * @param amount The amount to withdraw.
     */
    public void withdrawCurrentAccount(int amount) {
        double currentAmount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        double newAmount = currentAmount - amount;
        currentAccountDao.setAttribute("currentAccountAmount", newAmount);
        log.info("Withdraw current {} now {} -> {}", amount, currentAmount, newAmount);
    }

    /**
     * Formats the balance of the current account for display.
     *
     * @return The formatted balance of the current account.
     */
    public String toUiContent() {
        double balance = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        // Format to 2 decimal places and prepend "$"
        return "$" + String.format("%.2f", balance);
    }
}

