package com.seg83.childbank.service;

import com.seg83.childbank.dao.CurrentAccountDao;
import com.seg83.childbank.dao.HistoryActionsDao;
import com.seg83.childbank.utils.StringDateConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
public class InterestService {
    @Autowired
    private CurrentAccountDao currentAccountDao;

    @Autowired
    CurrentService currentService;

    @Autowired
    private HistoryActionsDao historyActionsDao;

    @Autowired
    private StringDateConvert convert;

    /**
     * Calculate the daily interest for the current accounts and update the history actions
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
    }
}
