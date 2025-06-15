package io.mac.employeetracker.employee;

public class EmployeeStatsDTO {
    public int total;
    public int newHires;
    public int contract;
    public int permanent;
    public int fullTime;
    public int partTime;
    public int endingSoon;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNewHires() {
        return newHires;
    }

    public void setNewHires(int newHires) {
        this.newHires = newHires;
    }

    public int getContract() {
        return contract;
    }

    public void setContract(int contract) {
        this.contract = contract;
    }

    public int getPermanent() {
        return permanent;
    }

    public void setPermanent(int permanent) {
        this.permanent = permanent;
    }

    public int getFullTime() {
        return fullTime;
    }

    public void setFullTime(int fullTime) {
        this.fullTime = fullTime;
    }

    public int getPartTime() {
        return partTime;
    }

    public void setPartTime(int partTime) {
        this.partTime = partTime;
    }

    public int getEndingSoon() {
        return endingSoon;
    }

    public void setEndingSoon(int endingSoon) {
        this.endingSoon = endingSoon;
    }
}
