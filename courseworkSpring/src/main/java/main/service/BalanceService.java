package main.service;

import main.entity.Balance;

public interface BalanceService
{
  void addBalance(Balance balance);
  void deleteBalance(int id);
}
