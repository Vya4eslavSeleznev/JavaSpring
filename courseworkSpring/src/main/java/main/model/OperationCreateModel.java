package main.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OperationCreateModel {
  private int articleId;
  private double debit;
  private double credit;
  private int balanceId;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createDate;

  public int getArticleId() {
    return articleId;
  }

  public void setArticleId(int articleId) {
    this.articleId = articleId;
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

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public int getBalanceId() {
    return balanceId;
  }

  public void setBalanceId(int balanceId) {
    this.balanceId = balanceId;
  }
}
