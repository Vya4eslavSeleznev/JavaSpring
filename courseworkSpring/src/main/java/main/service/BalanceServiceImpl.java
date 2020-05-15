package main.service;

import main.entity.Balance;
import main.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl implements BalanceService
{
  @Override
  public void addBalance(Balance balance)
  {
    balanceRepository.save(balance);
  }

  @Override
  public void deleteBalance(int id)
  {
    balanceRepository.deleteById(id);
  }

  @Autowired
  private BalanceRepository balanceRepository;
}
