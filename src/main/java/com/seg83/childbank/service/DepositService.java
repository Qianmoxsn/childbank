package com.seg83.childbank.service;

import com.seg83.childbank.dao.DepositAccountDao;
import com.seg83.childbank.dao.DepositAccountBillsDao;
import com.seg83.childbank.entity.DepositAccountBills;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DepositService {
    private final DepositAccountDao depositAccountDao;
    private final DepositAccountBillsDao depositAccountBillsDao;

    @Autowired
    public DepositService(DepositAccountDao depositAccountDao, DepositAccountBillsDao depositAccountBillsDao) {
        this.depositAccountDao = depositAccountDao;
        this.depositAccountBillsDao = depositAccountBillsDao;
    }

    public Object[][] generateDepositList() {
        log.info("Generating Deposit List...");
        List<DepositAccountBills> depositAccountBillsList = (List<DepositAccountBills>) depositAccountDao.getAttribute("depositAccount");

        // For swing JTable
        Object[][] data = new Object[depositAccountBillsList.size()][4];

        for (int i = 0; i < depositAccountBillsList.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = depositAccountBillsDao.getAttribute("depositAccountBillAmount", i + 1);
            data[i][2] = depositAccountBillsDao.getAttribute("depositAccountBillRate", i + 1);
            data[i][3] = depositAccountBillsDao.getAttribute("depositAccountBillExpireDate", i + 1);
        }
        return data;
    }

    public void createDepositAccountBill(double amount, double rate, String expireDate) {
        log.info("Creating depositAccountBill @ {} for {} {}", amount, rate, expireDate);
        depositAccountBillsDao.createDepositAccountBill(amount, rate, expireDate);
    }
}
