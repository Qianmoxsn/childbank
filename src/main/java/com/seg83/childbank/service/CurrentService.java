package com.seg83.childbank.service;

import com.seg83.childbank.dao.CurrentAccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurrentService {
    private final CurrentAccountDao currentAccountDao;

    @Autowired
    public CurrentService(CurrentAccountDao currentAccountDao) {
        this.currentAccountDao = currentAccountDao;
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
    }

    // TODO: 越界检测
    public void withdrawCurrentAccount(int amount) {
        double currentAmount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        double newAmount = currentAmount - amount;
        currentAccountDao.setAttribute("currentAccountAmount", newAmount);
        log.info("Withdraw current {} now {} -> {}", amount, currentAmount, newAmount);
    }

    public String toUiContent() {
        double balance =(Double) currentAccountDao.getAttribute("currentAccountAmount");
        // 保留2位小数，首部拼接“$”
        return "$" + String.format("%.2f", balance);
    }
}
