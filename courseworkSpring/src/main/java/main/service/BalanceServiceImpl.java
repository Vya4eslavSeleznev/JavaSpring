package main.service;

import main.entity.Balance;
import main.exception.BalanceNotFoundException;
import main.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BalanceServiceImpl implements BalanceService
{
  @Autowired
  private BalanceRepository balanceRepository;

  @Override
  public void addBalance(Balance balance)
  {
    balanceRepository.save(balance);
  }

  @Override
  public void deleteBalance(int id)
  {
    Optional<Balance> balance = balanceRepository.findById(id);

    if (!balance.isPresent())
      throw new BalanceNotFoundException("");

    balanceRepository.delete(balance.get());
  }
}
