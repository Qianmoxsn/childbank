package com.seg83.childbank.service;

import com.seg83.childbank.dao.CurrentAccountDao;
import com.seg83.childbank.dao.GoalDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * GoalService provides methods for managing goals and related operations.
 */
@Service
@Slf4j
public class GoalService {

    /**
     * GoalDao instance for handling goal data access.
     */
    private final GoalDao goalDao;

    /**
     * CurrentAccountDao instance for handling current account data access.
     */
    private final CurrentAccountDao currentAccountDao;

    /**
     * DepositService instance for handling deposit operations.
     */
    @Autowired
    private DepositService depositService;

    /**
     * The goal amount.
     */
    private double totalAmount;

    /**
     * The total amount (current + deposit).
     */
    private double goalAmount;

    /**
     * Constructor for creating a new GoalService instance.
     *
     * @param goalDao      GoalDao instance
     * @param currentAccountDao CurrentAccountDao instance
     */
    @Autowired
    public GoalService(GoalDao goalDao, CurrentAccountDao currentAccountDao) {
        this.goalDao = goalDao;
        this.currentAccountDao = currentAccountDao;
    }

    /**
     * Modifies the goal amount.
     *
     * @param value the new goal amount
     */
    public void modifyGoal(double value) {
        log.info("Modify goal amount to {}", value);
        goalDao.setAttribute("goalAmount", value);
    }

    /**
     * Calculates the progress towards the goal.
     *
     * @return the progress percentage (0-100)
     */
    public int calcGoal() {
        // Get goal amount
        this.goalAmount = (Double) goalDao.getAttribute("goalAmount");
        // Get total amount(current+deposit)
        double currAmount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        double depoAmount = depositService.calculateTotalDeposits();
        this.totalAmount = currAmount + depoAmount;

        if (this.totalAmount >= this.goalAmount) {
            return 100;
        } else {
            return (int) (this.totalAmount / this.goalAmount * 100);
        }
    }

    /**
     * Returns UI content based on the given parameter.
     *
     * @param param the parameter for the UI content
     * @return the UI content as a string
     */
    public String toUiContent(String param) {
        this.calcGoal();
        return switch (param) {
            // remain 2 decimal places
            case "goal" -> "$" + String.format("%.2f", this.goalAmount);
            case "total" -> "$" + String.format("%.2f", this.totalAmount);
            default -> "Invalid parameter";
        };
    }
}
