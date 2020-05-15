package main.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Operation
{
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  private Balance balance;

  @ManyToOne
  private Article article;

  @Column(name = "article_id", insertable = false, updatable = false)
  private int articleId;

  private double debit;
  private double credit;
  private Date createDate;

  @Column(name = "balance_id", insertable = false, updatable = false)
  private int balanceId;

  public Operation ()
  {
  }

  public Operation (int articleId, double debit, double credit, Date createDate, int balanceId)
  {
    this.articleId = articleId;
    this.debit = debit;
    this.credit = credit;
    this.createDate = createDate;
    this.balanceId = balanceId;
  }

  public int getId ()
  {
    return id;
  }

  public void setId (int id)
  {
    this.id = id;
  }

  public int getArticleId ()
  {
    return articleId;
  }

  public void setArticleId (int articleId)
  {
    this.articleId = articleId;
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

  public Date getCreateDate ()
  {
    return createDate;
  }

  public void setCreateDate (Date createDate)
  {
    this.createDate = createDate;
  }

  public int getBalanceId ()
  {
    return balanceId;
  }

  public void setBalanceId (int balanceId)
  {
    this.balanceId = balanceId;
  }
}

//Привязать поля к базе данных с помощью hibernate getmapping
//написать репозиторий(функционал, какие действия будут совершаться) интерфейс и от него класс с реализацией (логика доступа к данным)
//написать сервис - это то что содержит логику приложения (грубо говоря, вызываем методы с функционалом из репозитория)
//пишем контроллер - вызов сервиса

//далее аутентификация
//далее UI