package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONArray;
import com.seg83.childbank.entity.DepositAccountBills;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
public class DepositAccountBillsDao extends AbstractArrayDao {
    private final DepositAccountDao depositAccountDao;
    public long ElementCount;

    @Autowired
    public DepositAccountBillsDao(DepositAccountDao depositAccountDao) {
        this.depositAccountDao = depositAccountDao;
        // 修正 ElementCount 初始化
        this.getElementCount();
    }

    @Override
    void getElementCount() {
        log.info("Request deposit account count");
        this.ElementCount = this.load().size();
        log.debug("Get deposit account count {}", this.ElementCount);
    }

    @Override
    JSONArray load() {
        log.info("Request deposit account data in JSON format");
        JSONArray depositAccountBills = depositAccountDao.load().getJSONArray("depositAccountBills");
        log.debug("Get deposit account data {}", depositAccountBills);
        return depositAccountBills;
    }

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

    private void setDepositAccountBillAmount(long depositAccountBillId, double value) {
        DepositAccountBills bill = this.getElementById(depositAccountBillId);
        bill.setDepositAccountBillAmount(value);
        updateAccountBill(bill);
    }

    private void setDepositAccountBillRate(long depositAccountBillId, double value) {
        DepositAccountBills bill = this.getElementById(depositAccountBillId);
        bill.setDepositAccountBillRate(value);
        updateAccountBill(bill);
    }

    private void setDepositAccountBillExpireDate(long depositAccountBillId, String value) {
        DepositAccountBills bill = this.getElementById(depositAccountBillId);
        bill.setDepositAccountBillExpireDate(value);
        updateAccountBill(bill);
    }

    private void setDepositAccountBillEffectiveDate(long depositAccountBillId, String value) {
        DepositAccountBills bill = this.getElementById(depositAccountBillId);
        bill.setDepositAccountBillEffectiveDate(value);
        updateAccountBill(bill);
    }

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

    public void createDepositAccountBill(double amount, double rate, String effectiveDate, String expireDate) {
        log.info("Create DepositAccountBill with date amount {}, rate {}, effectiveDate {}, expireDate {}", amount, rate, effectiveDate, expireDate);
        DepositAccountBills newDepositAccountBills = new DepositAccountBills(this.ElementCount + 1, amount, rate, effectiveDate, expireDate);
        log.debug("this.ElementCount = {}", this.ElementCount);
        log.debug("DepositAccountBill created {}", newDepositAccountBills);

        List<DepositAccountBills> depositAccountBills = this.load().toJavaList(DepositAccountBills.class);
        depositAccountBills.add(newDepositAccountBills);
        this.ElementCount = depositAccountBills.size(); // 更新 ElementCount
        log.debug("Set DepositAccountBill Array {}", depositAccountBills);
        this.depositAccountDao.setAttribute("depositAccountBills", depositAccountBills);
    }

    public void deleteDepositAccountBill(long depositAccountBillId) {
        log.info("Delete DepositAccountBill with id {}", depositAccountBillId);
        List<DepositAccountBills> depositAccountBills = this.load().toJavaList(DepositAccountBills.class);
        depositAccountBills.removeIf(bill -> bill.getDepositAccountBillId() == depositAccountBillId);
        log.debug("Set DepositAccountBill Array after deletion {}", depositAccountBills);
        this.depositAccountDao.setAttribute("depositAccountBills", depositAccountBills);
        this.ElementCount = depositAccountBills.size(); // 更新 ElementCount
    }

    @Override
    public Object getAttribute(String attrname, long depositAccountBillId) {
        if (depositAccountBillId < 0 || depositAccountBillId > this.ElementCount) {
            throw new RuntimeException("Invalid Id range");
        }
        return switch (attrname) {
            case "depositAccountBillAmount" -> this.getDepositAccountBillAmount(depositAccountBillId);
            case "depositAccountBillRate" -> this.getDepositAccountBillRate(depositAccountBillId);
            case "depositAccountBillEffectiveDate" -> this.getDepositAccountBillEffectiveDate(depositAccountBillId);
            case "depositAccountBillExpireDate" -> this.getDepositAccountBillExpireDate(depositAccountBillId);
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    private Object getDepositAccountBillAmount(long depositAccountBillId) {
        DepositAccountBills bill = this.getElementById(depositAccountBillId);
        return bill.getDepositAccountBillAmount();
    }

    private Object getDepositAccountBillRate(long depositAccountBillId) {
        DepositAccountBills bill = this.getElementById(depositAccountBillId);
        return bill.getDepositAccountBillRate();
    }

    private Object getDepositAccountBillEffectiveDate(long depositAccountBillId) {
        DepositAccountBills bills = this.getElementById(depositAccountBillId);
        return bills.getDepositAccountBillEffectiveDate();
    }

    private Object getDepositAccountBillExpireDate(long depositAccountBillId) {
        DepositAccountBills bill = this.getElementById(depositAccountBillId);
        return bill.getDepositAccountBillExpireDate();
    }

    @Override
    public List<Object> getAllAttributes() {
        List<DepositAccountBills> depositAccountBills = this.load().toJavaList(DepositAccountBills.class);
        return List.copyOf(depositAccountBills);
    }

    public void deleteAllDepositAccountBills() {
        log.info("Deleting all deposit account bills");
        List<DepositAccountBills> depositAccountBills = this.load().toJavaList(DepositAccountBills.class);
        depositAccountBills.clear();
        this.depositAccountDao.setAttribute("depositAccountBills", depositAccountBills);
        this.ElementCount = 0;
    }
}
