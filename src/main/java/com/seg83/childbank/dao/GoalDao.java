package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.Goal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class GoalDao extends AbstractDao {
    private DataWrapperDao dataWrapperDao;

    @Autowired
    public GoalDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    @Override
    JSONObject load() {
        log.info("Request goal data in JSON format");
        JSONObject goal = dataWrapperDao.load().getJSONObject("goal");
        log.debug("Get goal data {}", goal);
        return goal;
    }

    @Override
    public void setAttribute(String attrname, Object value) {
        Goal modifiedGoal;
        switch (attrname) {
            case "goalAmount" -> {
                log.info("Setting goal amount to {}", value);
                modifiedGoal = this.setGoalAmount((double) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
        dataWrapperDao.setAttribute("goal", modifiedGoal);
    }

    private Goal setGoalAmount(double value) {
        Goal goal = this.load().toJavaObject(Goal.class);
        goal.setGoalAmount(value);
        return goal;
    }

    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "goalAmount" -> this.getGoalAmount();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    private Double getGoalAmount() {
        log.info("Get goal amount");
        double goalAmount = this.load().getDouble("goalAmount");
        log.debug("Goal amount is {}", goalAmount);
        return goalAmount;
    }

    @Override
    List<Object> getAllAttributes() {
        return List.of();
    }
}

