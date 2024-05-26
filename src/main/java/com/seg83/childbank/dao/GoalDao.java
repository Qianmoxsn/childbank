package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.Goal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object (DAO) for managing goal data.
 * Extends {@link AbstractDao} to handle goal data in JSON format.
 */
@Repository
@Slf4j
public class GoalDao extends AbstractDao {

    private DataWrapperDao dataWrapperDao;

    /**
     * Constructor for GoalDao.
     *
     * @param dataWrapperDao DataWrapperDao instance for managing data wrapper data.
     */
    @Autowired
    public GoalDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    /**
     * Load goal data in JSON format.
     *
     * @return JSONObject containing goal data.
     */
    @Override
    public JSONObject load() {
        log.info("Request goal data in JSON format");
        JSONObject goal = dataWrapperDao.load().getJSONObject("goal");
        log.debug("Get goal data {}", goal);
        return goal;
    }

    /**
     * Set an attribute of a goal.
     *
     * @param attrname   Name of the attribute to set.
     * @param value     Value to set for the attribute.
     */
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

    /**
     * Set the goal amount.
     *
     * @param value  Value to set for the goal amount.
     * @return Goal object with the updated goal amount.
     */
    private Goal setGoalAmount(double value) {
        Goal goal = this.load().toJavaObject(Goal.class);
        goal.setGoalAmount(value);
        return goal;
    }

    /**
     * Get an attribute of a goal.
     *
     * @param attrname   Name of the attribute to get.
     * @return Object containing the attribute value.
     */
    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "goalAmount" -> this.getGoalAmount();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    /**
     * Get the goal amount.
     *
     * @return Double containing the goal amount.
     */
    private Double getGoalAmount() {
        log.info("Get goal amount");
        double goalAmount = this.load().getDouble("goalAmount");
        log.debug("Goal amount is {}", goalAmount);
        return goalAmount;
    }

    /**
     * Get all attributes of a goal.
     *
     * @return List of Objects containing all attributes of a goal.
     */
    @Override
    public List<Object> getAllAttributes() {
        return List.of();
    }
}

