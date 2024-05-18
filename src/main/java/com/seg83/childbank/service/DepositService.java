package com.seg83.childbank.service;

import com.seg83.childbank.dao.AccountDao;
import com.seg83.childbank.dao.DepositAccountBillDao;
import com.seg83.childbank.entity.DepositAccountBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DepositService {
    private final AccountDao accountDao;
    private final DepositAccountBillDao depositAccountBillDao;

    @Autowired
    public DepositService(AccountDao accountDao, DepositAccountBillDao depositAccountBillDao) {
        this.accountDao = accountDao;
        this.depositAccountBillDao = depositAccountBillDao;
    }

    public Object[][] generateDepositList() {
        log.info("Generating Deposit List...");
        List<DepositAccountBill> depositAccountBillList = (List<DepositAccountBill>) accountDao.getAttribute("depositAccount");

        // For swing JTable
        Object[][] data = new Object[depositAccountBillList.size()][4];

        for (int i = 0; i < depositAccountBillList.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = depositAccountBillDao.getAttribute("depositAccountBillAmount", i + 1);
            data[i][2] = depositAccountBillDao.getAttribute("depositAccountBillRate", i + 1);
            data[i][3] = depositAccountBillDao.getAttribute("depositAccountBillExpireDate", i + 1);
        }
        return data;
    }

    public void createDepositAccountBill(double amount, double rate, String expireDate) {
        log.info("Creating depositAccountBill @ {} for {} {}", amount, rate, expireDate);
        depositAccountBillDao.createDepositAccountBill(amount, rate, expireDate);
    }
}
