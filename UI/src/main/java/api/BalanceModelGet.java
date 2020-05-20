package api;

import java.util.Date;

public class BalanceModelGet {
  private int id;
  private Date createDate;
  private double debit;
  private double credit;
  private double amount;

  public int getId() {
    return id;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public double getDebit() {
    return debit;
  }

  public double getCredit() {
    return credit;
  }

  public double getAmount() {
    return amount;
  }
}
