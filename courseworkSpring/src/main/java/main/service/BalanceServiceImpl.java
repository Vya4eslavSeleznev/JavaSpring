package main.service;

import main.entity.Balance;
import main.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceServiceImpl implements BalanceService
{
  @Override
  public List< Balance > getAll()
  {
    return (List<Balance>) balanceRepository.findAll();
  }

  @Autowired
  private BalanceRepository balanceRepository;
}
