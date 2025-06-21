package io.mac.employeetracker.employee;

public class EmployeeStatsDTO {
    public long total;
    public long newHires;
    public long contract;
    public long permanent;
    public long fullTime;
    public long partTime;
    public long endingSoon;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getNewHires() {
        return newHires;
    }

    public void setNewHires(long newHires) {
        this.newHires = newHires;
    }

    public long getContract() {
        return contract;
    }

    public void setContract(long contract) {
        this.contract = contract;
    }

    public long getPermanent() {
        return permanent;
    }

    public void setPermanent(long permanent) {
        this.permanent = permanent;
    }

    public long getFullTime() {
        return fullTime;
    }

    public void setFullTime(long fullTime) {
        this.fullTime = fullTime;
    }

    public long getPartTime() {
        return partTime;
    }

    public void setPartTime(long partTime) {
        this.partTime = partTime;
    }

    public long getEndingSoon() {
        return endingSoon;
    }

    public void setEndingSoon(long endingSoon) {
        this.endingSoon = endingSoon;
    }
}
