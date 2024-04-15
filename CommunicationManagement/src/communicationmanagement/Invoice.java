
package communicationmanagement;

import java.util.Date;

public class Invoice {

    private double usageLimit;
    private double currentSpending;
    private Date lastDayToPay;

    public Invoice(double usageLimit, Date lastDayToPay) {
        Date creationDate = new Date();
        long lastDayToPayTime = creationDate.getTime() + (30L * 24L * 60L * 1000L);
        this.lastDayToPay = new Date(lastDayToPayTime);
        this.usageLimit = usageLimit;
        this.currentSpending = 0.0;
    }

    public boolean isLimitExceeded(double amount) {
        if ((this.currentSpending + amount) > usageLimit) {
            return true;
        }
        return false;
    }

    public void addCost(double amount) {
        this.currentSpending += amount;
    }

    public void pay(double amount) {

        if (amount >= currentSpending) {
            amount -= currentSpending;
            currentSpending = 0.0;
            Date creationDateReset = new Date();
            long lastDayToPayTimeReset = creationDateReset.getTime() + (30L * 24L * 60L * 1000L);
            this.lastDayToPay = new Date(lastDayToPayTimeReset);
        } else {
            currentSpending -= amount;
        }
    }

    public void changeUsageLimit(double amount) {
        usageLimit = amount;
    }

    public double getUsageLimit() {
        return usageLimit;
    }

    public double getCurrentSpending() {
        return currentSpending;
    }

    public Date getLastDayToPay() {
        return lastDayToPay;
    }

    public void setLastDayToPay() {
        Date creationDateReset = new Date();
        long lastDayToPayTimeReset = creationDateReset.getTime() + (30L * 24L * 60L * 1000L);
        this.lastDayToPay = new Date(lastDayToPayTimeReset);
    }
}
