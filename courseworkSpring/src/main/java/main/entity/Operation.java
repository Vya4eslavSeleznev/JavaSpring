package main.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Operation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  private Balance balance;

  @ManyToOne
  private Article article;

  @Column(nullable = false, name = "article_id", insertable = false, updatable = false)
  private int articleId;

  @Column(nullable = false)
  private double debit;

  @Column(nullable = false)
  private double credit;

  @Column(nullable = false)
  private Date createDate;

  @Column(nullable = false, name = "balance_id", insertable = false, updatable = false)
  private int balanceId;

  public Operation() {
  }

  public Operation(Article article, Balance balance, double debit, double credit, Date createDate) {
    this.debit = debit;
    this.credit = credit;
    this.createDate = createDate;
    this.article = article;
    this.balance = balance;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

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
