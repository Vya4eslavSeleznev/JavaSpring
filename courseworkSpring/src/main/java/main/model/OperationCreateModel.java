package main.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OperationCreateModel {
  public int articleId;
  public double debit;
  public double credit;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  public Date createDate;
  public int balanceId;
}
