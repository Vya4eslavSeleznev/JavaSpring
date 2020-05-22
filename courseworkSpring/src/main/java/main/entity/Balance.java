package main.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Balance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false, name = "create_date")
  private Date createDate;

  @Column(nullable = false)
  private double debit;

  @Column(nullable = false)
  private double credit;

  @Column(nullable = false)
  private double amount;

  public Balance() {
  }

  public Balance(Date createDate, double debit, double credit) {
    this.createDate = createDate;
    this.debit = debit;
    this.credit = credit;
    this.updateAmount();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

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
    this.updateAmount();
  }

  public double getCredit() {
    return credit;
  }

  public void setCredit(double credit) {
    this.credit = credit;
    this.updateAmount();
  }

  public double getAmount() {
    return amount;
  }

  private void updateAmount() {
    this.amount = this.debit - this.credit;
  }
}
