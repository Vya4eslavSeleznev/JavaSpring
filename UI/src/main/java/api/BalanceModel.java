package api;

import java.util.Date;

public class BalanceModel {
  private Date createDate;
  private double debit;
  private double credit;

  public BalanceModel(Date createDate, double debit, double credit)
  {
    this.createDate = createDate;
    this.debit = debit;
    this.credit = credit;
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
}
