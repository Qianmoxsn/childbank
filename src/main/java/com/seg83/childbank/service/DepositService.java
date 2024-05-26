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

/**
 * DepositService provides methods for managing deposit accounts and related operations.
 */
@Service
@Slf4j
public class DepositService {

    /**
     * DepositAccountDao instance for handling deposit account data access.
     */
    private final DepositAccountDao depositAccountDao;

    /**
     * CurrentService instance for handling current account operations.
     */
    private final CurrentService currentService;

    /**
     * DepositAccountBillsDao instance for handling deposit account bill data access.
     */
    private final DepositAccountBillsDao depositAccountBillsDao;

    /**
     * HistoryService instance for handling operation history data access.
     */
    private final HistoryService historyService;

    /**
     * StringDateConvert instance for converting date strings to LocalDate objects.
     */
    private final StringDateConvert convert;

    /**
     * Label for displaying the total fixed balance.
     */
    private JLabel totalFixedBalanceLabel;

    /**
     * Constructor for creating a new DepositService instance.
     *
     * @param depositAccountDao      DepositAccountDao instance
     * @param depositAccountBillsDao  DepositAccountBillsDao instance
     * @param historyService         HistoryService instance
     * @param currentService         CurrentService instance
     * @param convert                StringDateConvert instance
     */
    @Autowired
    public DepositService(DepositAccountDao depositAccountDao, DepositAccountBillsDao depositAccountBillsDao, HistoryService historyService, CurrentService currentService, StringDateConvert convert) {
        this.depositAccountDao = depositAccountDao;
        this.currentService = currentService;
        this.depositAccountBillsDao = depositAccountBillsDao;
        this.historyService = historyService;
        this.convert = convert;
    }

    /**
     * Generates a list of deposit accounts for display in a JTable.
     *
     * @return a 2D array of objects representing the deposit accounts
     */
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

    /**
     * Creates a new deposit account bill.
     *
     * @param amount        the amount of the deposit
     * @param rate         the interest rate for the deposit
     * @param effectiveDate the effective date of the deposit
     * @param expireDate   the expiration date of the deposit
     */
    public void createDepositAccountBill(double amount, double rate, String effectiveDate, String expireDate) {
        log.info("Creating depositAccountBill @ {} for {} (from {} to{})", amount, rate, effectiveDate, expireDate);
        depositAccountBillsDao.createDepositAccountBill(amount, rate, effectiveDate, expireDate);
    }
    /**
     * Deposits a fixed amount into the fix account.
     *
     * @param amount        the amount to deposit
     * @param rate         the interest rate for the deposit
     * @param effectiveDate the effective date of the deposit
     * @param expireDate   the expiration date of the deposit
     * @return true if the deposit was successful, false otherwise
     */
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

    /**
     * Processes matured deposits and transfers the interest to the current account.
     */
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

    /**
     * Calculates the total amount of deposits.
     *
     * @return the total amount of deposits
     */
    public double calculateTotalDeposits() {
        double total = 0;
        for (int i = 0; i < depositAccountBillsDao.ElementCount; i++) {
            total = total + (double) depositAccountBillsDao.getAttribute("depositAccountBillAmount", i + 1);
        }
        return  total;
    }
}

