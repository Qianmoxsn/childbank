package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONArray;
import com.seg83.childbank.entity.DepositAccountBills;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for managing deposit account bills.
 * Extends {@link AbstractArrayDao} to handle deposit account bills data in JSON format.
 */
@Repository
@Slf4j
public class DepositAccountBillsDao extends AbstractArrayDao {

    private final DepositAccountDao depositAccountDao;

    /**
     * Constructor for DepositAccountBillsDao.
     *
     * @param depositAccountDao DepositAccountDao instance for managing deposit account data.
     */
    @Autowired
    public DepositAccountBillsDao(DepositAccountDao depositAccountDao) {
        this.depositAccountDao = depositAccountDao;
        getElementCount();
    }

    /**
     * Get the number of deposit account bills.
     */
    @Override
    void getElementCount() {
        log.info("Request deposit account count");
        this.ElementCount = this.load().size();
        log.debug("Get deposit account count {}", this.ElementCount);
    }

    /**
     * Load deposit account bills data in JSON format.
     *
     * @return JSONArray containing deposit account bills data.
     */
    @Override
    JSONArray load() {
        log.info("Request deposit account data in JSON format");
        JSONArray depositAccountBills = depositAccountDao.load().getJSONArray("depositAccountBills");
        log.debug("Get deposit account data {}", depositAccountBills);
        return depositAccountBills;
    }

    /**
     * Get a deposit account bill by its ID.
     *
     * @param depositAccountBillId ID of the deposit account bill to retrieve.
     * @return DepositAccountBills object with the specified ID.
     */
    @Override
    DepositAccountBills getElementById(long depositAccountBillId) {
        log.info("Request depositAccountBill with id {}", depositAccountBillId);
        List<DepositAccountBills> depositAccountBills = this.load().toJavaList(DepositAccountBills.class);
        for (DepositAccountBills bill : depositAccountBills) {
            if (bill.getDepositAccountBillId() == depositAccountBillId) {
                log.debug("Get depositAccountBills {}", bill);
                return bill;
            }
        }
        throw new RuntimeException("Invalid Id");
    }

    /**
     * Set an attribute of a deposit account bill.
     *
     * @param attrname             Name of the attribute to set.
     * @param value                Value to set for the attribute.
     * @param depositAccountBillId ID of the deposit account bill to update.
     */
    @Override
    void setAttribute(String attrname, Object value, long depositAccountBillId) {
        switch (attrname) {
            case "depositAccountBillAmount" -> {
                log.info("Set depositAccountBillAmount to {}", value);
                this.setDepositAccountBillAmount(depositAccountBillId, (double) value);
            }
            case "depositAccountBillRate" -> {
                log.info("Set depositAccountBillRate to {}", value);
                this.setDepositAccountBillRate(depositAccountBillId, (double) value);
            }
            case "depositAccountBillExpireDate" -> {
                log.info("Set depositAccountBillExpireDate to {}", value);
                this.setDepositAccountBillExpireDate(depositAccountBillId, (String) value);
            }
            case "depositAccountBillEffectiveDate" -> {
                log.info("Set depositAccountBillEffectiveDate to {} ", value);
                this.setDepositAccountBillEffectiveDate(depositAccountBillId, (String) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
    }

    /**
     * Set the deposit account bill amount.
     *
     * @param depositAccountBillId ID of the deposit account bill to update.
     * @param value                Value to set for the deposit account bill amount.
     */
    private void setDepositAccountBillAmount(long depositAccountBillId, double value
}
            case"depositAccountBillRate"->{
                    log.

info("Set depositAccountBillRate to {}",value);
                this.

setDepositAccountBillRate(depositAccountBillId, (double) value);
        }
        case"depositAccountBillExpireDate"->{
        log.

info("Set depositAccountBillExpireDate to {}",value);
                this.

setDepositAccountBillExpireDate(depositAccountBillId, (String) value);
        }
        case"depositAccountBillEffectiveDate"->{
        log.

info("Set depositAccountBillEffectiveDate to {} ",value);
                this.

setDepositAccountBillEffectiveDate(depositAccountBillId, (String) value);
        }
default ->throw new

RuntimeException("Invalid attribute name");
        }
                }

/**
 * Set the deposit account bill rate.
 *
 * @param depositAccountBillId ID of the deposit account bill to update.
 * @param value                Value to set for the deposit account bill rate.
 */
private void setDepositAccountBillRate(long depositAccountBillId, double value) {
    DepositAccountBills bill = this.getElementById(depositAccountBillId);
    bill.setDepositAccountBillRate(value);
    updateAccountBill(bill);
}

/**
 * Set the deposit account bill expire date.
 *
 * @param depositAccountBillId ID of the deposit account bill to update.
 * @param value                Value to set for the deposit account bill expire date.
 */
private void setDepositAccountBillExpireDate(long depositAccountBillId, String value) {
    DepositAccountBills bill = this.getElementById(depositAccountBillId);
    bill.setDepositAccountBillExpireDate(value);
    updateAccountBill(bill);
}

/**
 * Set the deposit account bill effective date.
 *
 * @param depositAccountBillId ID of the deposit account bill to update.
 * @param value                Value to set for the deposit account bill effective date.
 */
private void setDepositAccountBillEffectiveDate(long depositAccountBillId, String value) {
    DepositAccountBills bill = this.getElementById(depositAccountBillId);
    bill.setDepositAccountBillEffectiveDate(value);
    updateAccountBill(bill);
}

/**
 * Update the deposit account bill.
 *
 * @param bill DepositAccountBills object to update.
 */
private void updateAccountBill(DepositAccountBills bill) {
    List<DepositAccountBills> depositAccountBills = this.load().toJavaList(DepositAccountBills.class);
    for (int i = 0; i < depositAccountBills.size(); i++) {
        if (depositAccountBills.get(i).getDepositAccountBillId() == bill.getDepositAccountBillId()) {
            depositAccountBills.set(i, bill);
            break;
        }
    }
    log.debug("Update DepositAccountBill Array {}", depositAccountBills);
    this.depositAccountDao.setAttribute("depositAccountBills", depositAccountBills);
}

/**
 * Create a new deposit account bill.
 *
 * @param amount        Value to set for the deposit account bill amount.
 * @param rate          Value to set for the deposit account bill rate.
 * @param effectiveDate Value to set for the deposit account bill effective date.
 * @param expireDate    Value to set for the deposit account bill expire date.
 */
public void createDepositAccountBill(double amount, double rate, String effectiveDate, String expireDate) {
    log.info("Create DepositAccountBill with date amount {}, rate {}, effectiveDate {}, expireDate {}", amount, rate, effectiveDate, expireDate);
    DepositAccountBills newDepositAccountBills = new DepositAccountBills(this.ElementCount + 1, amount, rate, effectiveDate, expireDate);
    log.debug("this.ElementCount = {}", this.ElementCount);
    log.debug("DepositAccountBill created {}", newDepositAccountBills);

    List<DepositAccountBills> depositAccountBills = this.load().toJava
    List<DepositAccountBills> depositAccountBills = this.load().toJavaList(DepositAccountBills.class);
    depositAccountBills.add(newDepositAccountBills);
    this.ElementCount = depositAccountBills.size(); // 更新 ElementCount
    log.debug("Set DepositAccountBill Array {}", depositAccountBills);
    this.depositAccountDao.setAttribute("depositAccountBills", depositAccountBills);
}

/**
 * Delete a deposit account bill.
 *
 * @param depositAccountBillId ID of the deposit account bill to delete.
 */
public void deleteDepositAccountBill(long depositAccountBillId) {
    log.info("Delete DepositAccountBill with id {}", depositAccountBillId);
    List<DepositAccountBills> depositAccountBills = this.load().toJavaList(DepositAccountBills.class);
    depositAccountBills.removeIf(bill -> bill.getDepositAccountBillId() == depositAccountBillId);
    log.debug("Set DepositAccountBill Array after deletion {}", depositAccountBills);
    this.depositAccountDao.setAttribute("depositAccountBills", depositAccountBills);
    this.ElementCount = depositAccountBills.size(); // 更新 ElementCount
}

/**
 * Get an attribute of a deposit account bill.
 *
 * @param attrname             Name of the attribute to get.
 * @param depositAccountBillId ID of the deposit account bill to retrieve.
 * @return Object containing the attribute value.
 */
@Override
public Object getAttribute(String attrname, long depositAccountBillId) {
    if (depositAccountBillId < 0 || depositAccountBillId > this.ElementCount) {
        throw new RuntimeException("Invalid Id range");
    }
    return switch (attrname) {
        case "depositAccountBillAmount" -> this.getDepositAccountBillAmount(depositAccountBillId);
        case "depositAccountBillRate" -> this.getDepositAccountBillRate(depositAccountBillId);
        case "depositAccountBillExpireDate" -> this.getDepositAccountBillExpireDate(depositAccountBillId);
        case "depositAccountBillEffectiveDate" -> this.getDepositAccountBillEffectiveDate(depositAccountBillId);
        default -> throw new RuntimeException("Invalid attribute name");
    };
}

/**
 * Get the deposit account bill amount.
 *
 * @param depositAccountBillId ID of the deposit account bill to retrieve.
 * @return Double containing the deposit account bill amount.
 */
private Object getDepositAccountBillAmount(long depositAccountBillId) {
    DepositAccountBills bill = this.getElementById(depositAccountBillId);
    return bill.getDepositAccountBillAmount();
}

/**
 * Get the deposit account bill rate.
 *
 * @param depositAccountBillId ID of the deposit account bill to retrieve.
 * @return Double containing the deposit account bill rate.
 */
private Object getDepositAccountBillRate(long depositAccountBillId) {
    DepositAccountBills bill = this.getElementById(depositAccountBillId);
    return bill.getDepositAccountBillRate();
}

/**
 * Get the deposit account bill expire date.
 *
 * @param depositAccountBillId ID of the deposit account bill to retrieve.
 * @return String containing the deposit account bill expire date.
 */
private Object getDepositAccountBillExpireDate(long depositAccountBillId) {
    DepositAccountBills bill = this.getElementById(depositAccountBillId);
    return bill.getDepositAccountBillExpireDate();
}

/**
 * Get the deposit account bill effective date.
 *
 * @param depositAccountBillId ID of the deposit account bill to retrieve.
 * @return String containing the deposit account bill effective date.
 */
private Object getDepositAccountBillEffectiveDate(long depositAccountBillId) {
    DepositAccountBills bill = this.getElementById(depositAccountBillId);
    return bill.getDepositAccountBillEffectiveDate();
}

/**
 * Get all attributes
 * Get all attributes of a deposit account bill.
 *
 * @return List of Objects containing all attributes of a deposit account bill.
 */
@Override
public List<Object> getAllAttributes() {
    List<DepositAccountBills> depositAccountBills = this.load().toJavaList(DepositAccountBills.class);
    return List.copyOf(depositAccountBills);
}

/**
 * Delete all deposit account bills.
 */
public void deleteAllDepositAccountBills() {
    log.info("Deleting all deposit account bills");
    List<DepositAccountBills> depositAccountBills = this.load().toJavaList(DepositAccountBills.class);
    depositAccountBills.clear();
    this.depositAccountDao.setAttribute("depositAccountBills", depositAccountBills);
    this.ElementCount = 0;
}
}
