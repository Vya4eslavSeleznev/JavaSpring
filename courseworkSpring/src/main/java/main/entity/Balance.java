package main.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Balance
{
  @Id
  @GeneratedValue (strategy = GenerationType.AUTO)
  private int id;

  private String createDate;
  private double debit;
  private double credit;
  private double amount;

  public Balance ()
  {
  }

  public Balance (int id, String createDate, double debit, double credit, double amount)
  {
    this.id = id;
    this.createDate = createDate;
    this.debit = debit;
    this.credit = credit;
    this.amount = amount;
  }

  public int getId ()
  {
    return id;
  }

  public void setId (int id)
  {
    this.id = id;
  }

  public String getCreateDate ()
  {
    return createDate;
  }

  public void setCreateDate (String createDate)
  {
    this.createDate = createDate;
  }

  public double getDebit ()
  {
    return debit;
  }

  public void setDebit (double debit)
  {
    this.debit = debit;
  }

  public double getCredit ()
  {
    return credit;
  }

  public void setCredit (double credit)
  {
    this.credit = credit;
  }

  public double getAmount ()
  {
    return amount;
  }

  public void setAmount (double amount)
  {
    this.amount = amount;
  }
}
