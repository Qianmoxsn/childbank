package com.seg83.childbank.service;

import com.seg83.childbank.dao.DepositAccountDao;
import com.seg83.childbank.dao.DepositAccountBillsDao;
import com.seg83.childbank.entity.DepositAccountBills;
import com.seg83.childbank.utils.StringDateConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DepositService {
    private final DepositAccountDao depositAccountDao;
    private final CurrentService currentService;
    private final DepositAccountBillsDao depositAccountBillsDao;
    private final HistoryService historyService;
    private final StringDateConvert convert;
    private JLabel totalFixedBalanceLabel;

    @Autowired
    public DepositService(DepositAccountDao depositAccountDao, DepositAccountBillsDao depositAccountBillsDao, HistoryService historyService, CurrentService currentService, StringDateConvert convert) {
        this.depositAccountDao = depositAccountDao;
        this.currentService = currentService;
        this.depositAccountBillsDao = depositAccountBillsDao;
        this.historyService = historyService;
        this.convert = convert;

    }

    public Object[][] generateDepositList() {
        log.info("Generating Deposit List...");
        List<DepositAccountBills> depositAccountBillsList = (List<DepositAccountBills>) depositAccountDao.getAttribute("depositAccount");

        // For swing JTable
        Object[][] data = new Object[depositAccountBillsList.size()][5];

        for (int i = 0; i < depositAccountBillsList.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = depositAccountBillsDao.getAttribute("depositAccountBillAmount", i + 1);
            data[i][2] = depositAccountBillsDao.getAttribute("depositAccountBillRate", i + 1);
            data[i][3] = depositAccountBillsDao.getAttribute("depositAccountBillEffectiveDate", i + 1);
            data[i][4] = depositAccountBillsDao.getAttribute("depositAccountBillExpireDate", i + 1);
        }
        return data;
    }

    public void createDepositAccountBill(double amount, double rate, String effectiveDate, String expireDate) {
        log.info("Creating depositAccountBill @ {} for {} (from {} to{})", amount, rate, effectiveDate, expireDate);
        depositAccountBillsDao.createDepositAccountBill(amount, rate, effectiveDate, expireDate);
    }

    public boolean depositFixAccount(double amount, double rate, String effectiveDate, String expireDate) {
        if (currentService.withdrawCurrentAccount((int) amount)) {
            createDepositAccountBill(amount, rate, effectiveDate, expireDate);
            log.info("Deposit fix {}", amount);
            historyService.createOperationHistory(amount, "Fix deposit");
            return true;
        }else {
            log.error("Deposit fix failed");
            return false;
        }
    }

    public void processMaturedDeposits() {
        log.info("Processing matured deposits...");
        List<DepositAccountBills> depositAccountBillsList = (List<DepositAccountBills>) depositAccountDao.getAttribute("depositAccount");
        LocalDate today = LocalDate.now();

        for (DepositAccountBills bill : depositAccountBillsList) {
            LocalDate expireDate = LocalDate.parse(bill.getDepositAccountBillExpireDate(), DateTimeFormatter.ISO_DATE);
            if (!expireDate.isAfter(today)) {
                double amount = bill.getDepositAccountBillAmount();
                double rate = bill.getDepositAccountBillRate();
                long days = convert.calculateDaysBetween(bill.getDepositAccountBillEffectiveDate(), bill.getDepositAccountBillExpireDate());
                double interest = amount * rate * days / 365;
                double totalAmount = amount + interest;
                // To .2 decimal places
                totalAmount = Math.round(totalAmount * 100) / 100.0;

                currentService.depositCurrentAccount(totalAmount);
                historyService.createOperationHistory(totalAmount, "Deposit Expire");
                depositAccountBillsDao.deleteDepositAccountBill(bill.getDepositAccountBillId());
                log.info("Processed matured deposit with amount: {}, interest: {}, total: {}", amount, interest, totalAmount);
            }
        }
    }

    public double calculateTotalDeposits() {
        double total = 0;
        for (int i = 0; i < depositAccountBillsDao.ElementCount; i++) {
            total = total + (double) depositAccountBillsDao.getAttribute("depositAccountBillAmount", i + 1);
        }
        return  total;
    }

}
