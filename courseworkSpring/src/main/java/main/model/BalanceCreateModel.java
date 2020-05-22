package main.model;

import java.util.Date;

public class BalanceCreateModel {
  private Date createDate;
  private double debit;
  private double credit;

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public double getDebit() {
    return debit;
  }

  public void setDebit(double debit) {
    this.debit = debit;
  }

  public double getCredit() {
    return credit;
  }

  public void setCredit(double credit) {
    this.credit = credit;
  }
}
