package main.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Balance
{
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id;

  @Column (name = "create_date")
  private Date createDate;

  private double debit;
  private double credit;
  private double amount;

  public Balance ()
  {
  }

  public Balance(Date createDate, double debit, double credit, double amount)
  {
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

  public Date getCreateDate ()
  {
    return createDate;
  }

  public void setCreateDate (Date createDate)
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
