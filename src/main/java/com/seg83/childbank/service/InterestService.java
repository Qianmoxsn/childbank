package com.seg83.childbank.service;

import com.seg83.childbank.dao.CurrentAccountDao;
import com.seg83.childbank.dao.HistoryActionsDao;
import com.seg83.childbank.utils.StringDateConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * InterestService provides methods for managing current account interest calculations and related operations.
 */
@Service
@Slf4j
public class InterestService {

    /**
     * CurrentAccountDao instance for handling current account data access.
     */
    @Autowired
    private CurrentAccountDao currentAccountDao;

    /**
     * CurrentService instance for handling current account operations.
     */
    @Autowired
    private CurrentService currentService;

    /**
     * HistoryActionsDao instance for handling history actions data access.
     */
    @Autowired
    private HistoryActionsDao historyActionsDao;

    /**
     * StringDateConvert instance for converting date strings to Date objects.
     */
    @Autowired
    private StringDateConvert convert;

    /**
     * Calculates the daily interest for the current accounts and updates the history actions.
     * <p>
     * Interest rate is "currentAccountRate"% per day Calculated and added to account when the app starts
     */
    private void dailyCurrentInterest() {
        // Get the current account rate and amount
        double currentAccountRate = (double) currentAccountDao.getAttribute("currentAccountRate") / 100;
        double currentAccountsAmount = (double) currentAccountDao.getAttribute("currentAccountAmount");

        // Calculate the interest (2-decimal precision）
        double interest = Math.round(currentAccountsAmount * currentAccountRate * 100) / 100.0;
        log.info("Interest for today: {}", interest);

        // Add to the current account
        currentService.payDailyCurrentInterest(interest);
    }

    /**
     * Calculates the current account interest based on the number of days since the last interest payment.
     */
    public void calculateCurrentInterest() {
        // Check days not paid the interest
        Date lastInterestDate = convert.StringToDate((String) currentAccountDao.getAttribute("lastInterestDate"));
        Date currentDate = new Date();

        Calendar calLast = Calendar.getInstance();
        calLast.setTime(lastInterestDate);
        Calendar calCurrent = Calendar.getInstance();
        calCurrent.setTime(currentDate);

        long diffMillis = calCurrent.getTimeInMillis() - calLast.getTimeInMillis();
        long diffDays = diffMillis / (24 * 60 * 60 * 1000);
        log.info("Days since last interest: {}", diffDays);

        // Calculate the interest for each day
        for (int i = 0; i < diffDays; i++) {
            dailyCurrentInterest();
        }

        // Update the last interest date
        currentAccountDao.setAttribute("lastInterestDate", convert.DateToString(currentDate));
    }
}
