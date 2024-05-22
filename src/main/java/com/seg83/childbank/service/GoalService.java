package com.seg83.childbank.service;

import com.seg83.childbank.dao.CurrentAccountDao;
import com.seg83.childbank.dao.GoalDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GoalService {
    private final GoalDao goalDao;
    private final CurrentAccountDao currentAccountDao;

    private double goalAmount;
    private double totalAmount;

    @Autowired
    public GoalService(GoalDao goalDao, CurrentAccountDao currentAccountDao) {
        this.goalDao = goalDao;
        this.currentAccountDao = currentAccountDao;
    }

    public void modifyGoal(double value) {
        log.info("Modify goal amount to {}", value);
        goalDao.setAttribute("goalAmount", value);
    }

    // TODO: Implement with deposit
    public int calcGoal() {
        // Get goal amount
        this.goalAmount = (Double) goalDao.getAttribute("goalAmount");
        // Get total amount(current+deposit)
        double currAmount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        double depoAmount = 0.0;
        this.totalAmount = currAmount + depoAmount;

        if (this.totalAmount >= this.goalAmount) {
            return 100;
        } else {
            return (int) (this.totalAmount / this.goalAmount * 100);
        }
    }

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
