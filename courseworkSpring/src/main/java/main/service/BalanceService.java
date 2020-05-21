package main.service;

import main.entity.Balance;
import main.entity.Operation;
import main.model.FilterModel;

import java.util.List;

public interface BalanceService {
  void addBalance(Balance balance);

  void deleteBalance(int id);

  List<Balance> listBalances();

  List<Balance> getBalanceWithFilter(FilterModel filter);

  List<Operation> getOperationByBalanceId(int id);
}
