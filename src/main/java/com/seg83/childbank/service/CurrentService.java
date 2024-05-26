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
    private final HistoryService historyService;

    /**
     * Constructs a new CurrentService with the specified CurrentAccountDao.
     *
     * @param currentAccountDao The data access object for the current account.
     */
    @Autowired
    public CurrentService(CurrentAccountDao currentAccountDao, HistoryService historyService) {
        this.currentAccountDao = currentAccountDao;
        this.historyService = historyService;
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
    public void depositCurrentAccount(double amount) {
        double currentAmount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        double newAmount = currentAmount + amount;
        currentAccountDao.setAttribute("currentAccountAmount", newAmount);
        log.info("Deposit current {} now {} -> {}", amount, currentAmount, newAmount);
        // Create a history
        historyService.createOperationHistory(amount, "current deposit");
    }

    public void payDailyCurrentInterest(double amount) {
        double currentAmount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        double newAmount = currentAmount + amount;
        currentAccountDao.setAttribute("currentAccountAmount", newAmount);
        log.info("Deposit current {} now {} -> {}", amount, currentAmount, newAmount);
        // Create a history
        historyService.createOperationHistory(amount, "daily interest");
    }

    // TODO: Overflow detection

    /**
     * Withdraws an amount from the current account.
     *
     * @param amount The amount to withdraw.
     */
    public boolean withdrawCurrentAccount(int amount) {
        double currentAmount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        double newAmount = currentAmount - amount;
        if (newAmount < 0) {
            log.error("Withdraw current {} failed, not enough balance", amount);
            return false;
        }
        currentAccountDao.setAttribute("currentAccountAmount", newAmount);
        log.info("Withdraw current {} now {} -> {}", amount, currentAmount, newAmount);
        // Create a history
        historyService.createOperationHistory(amount, "current withdraw");
        return true;
    }

    public void modifyInterestRate(double rate) {
        currentAccountDao.setAttribute("currentAccountRate", rate);
        log.info("Set interest rate to {}", rate);
    }

    /**
     * Formats the balance of the current account for display.
     *
     * @return The formatted balance of the current account.
     */
    public String amountToUiContent() {
        double balance = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        // Format to 2 decimal places and prepend "$"
        return "$" + String.format("%.2f", balance);
    }

    public String rateToUiContent(){
        double rate = (Double) currentAccountDao.getAttribute("currentAccountRate");
        // Format to 2 decimal places and tail "%"
        return String.format("%.2f", rate) + "%";
    }
}
