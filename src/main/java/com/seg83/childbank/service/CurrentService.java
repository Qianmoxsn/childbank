package com.seg83.childbank.service;

import com.seg83.childbank.dao.CurrentAccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurrentService {
    private final CurrentAccountDao currentAccountDao;
    private final HistoryService historyService;

    @Autowired
    public CurrentService(CurrentAccountDao currentAccountDao, HistoryService historyService) {
        this.currentAccountDao = currentAccountDao;
        this.historyService = historyService;
    }

    public Double checkCurrentAccountBalance() {
        log.info("Checking Current Account Balance");
        return (Double) currentAccountDao.getAttribute("currentAccountAmount");
    }

    // TODO: 越界检测
    public void depositCurrentAccount(int amount) {
        double currentAmount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        double newAmount = currentAmount + amount;
        currentAccountDao.setAttribute("currentAccountAmount", newAmount);
        log.info("Deposit current {} now {} -> {}", amount, currentAmount, newAmount);
        // Create a history
        historyService.createOperationHistory(amount, "current deposit");
    }

    // TODO: 越界检测
    public void withdrawCurrentAccount(int amount) {
        double currentAmount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        double newAmount = currentAmount - amount;
        currentAccountDao.setAttribute("currentAccountAmount", newAmount);
        log.info("Withdraw current {} now {} -> {}", amount, currentAmount, newAmount);
        // Create a history
        historyService.createOperationHistory(amount, "current withdraw");
    }

    public String toUiContent() {
        double balance = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        // 保留2位小数，首部拼接“$”
        return "$" + String.format("%.2f", balance);
    }
}
