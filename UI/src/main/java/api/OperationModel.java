package api;

import java.util.Date;

public class OperationModel {
  private int articleId;
  private double debit;
  private double credit;
  private Date createDate;
  private int balanceId;

  public OperationModel(int articleId, double debit, double credit, Date createDate, int balanceId)
  {
    this.articleId = articleId;
    this.debit = debit;
    this.credit = credit;
    this.createDate = createDate;
    this.balanceId = balanceId;
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
