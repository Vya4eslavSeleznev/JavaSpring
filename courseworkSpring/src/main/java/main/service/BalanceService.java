package main.service;

import main.entity.Balance;
import main.model.FilterModel;

import java.util.List;

public interface BalanceService
{
  void addBalance(Balance balance);
  void deleteBalance(int id);
  List<Balance> getBalanceWithFilter(FilterModel filter);
}
