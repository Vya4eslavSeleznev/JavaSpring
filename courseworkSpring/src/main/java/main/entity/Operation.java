package main.entity;

import javax.persistence.*;

@Entity
public class Operation
{
  public Operation ()
  {
  }

  public Operation (int id, int articleId, double debit, double credit, String createDate, int balanceId)
  {
    this.id = id;
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

  public String getCreateDate ()
  {
    return createDate;
  }

  public void setCreateDate (String createDate)
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

  @Id
  @GeneratedValue (strategy = GenerationType.AUTO)
  private int id;

  @ManyToOne
  private Balance balance;

  @ManyToOne
  private Article article;

  private int articleId;
  private double debit;
  private double credit;
  private String createDate;
  private int balanceId;
}

//Привязать поля к базе данных с помощью hibernate getmapping
//написать репозиторий(функционал, какие действия будут совершаться) интерфейс и от него класс с реализацией (логика доступа к данным)
//написать сервис - это то что содержит логику приложения (грубо говоря, вызываем методы с функционалом из репозитория)
//пишем контроллер - вызов сервиса

//далее аутентификация
//далее UI