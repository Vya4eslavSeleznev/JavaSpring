package api;

import java.util.Date;

public class OperationModelGet {
  private int id;
  private int articleId;
  private double debit;
  private double credit;
  private Date createDate;
  private int balanceId;

  public int getId() {
    return id;
  }

  public int getArticleId() {
    return articleId;
  }

  public double getDebit() {
    return debit;
  }

  public double getCredit() {
    return credit;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public int getBalanceId() {
    return balanceId;
  }
}
