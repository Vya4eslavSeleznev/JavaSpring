package main.service;

import main.entity.Balance;
import main.exception.BalanceNotFoundException;
import main.model.FilterModel;
import main.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BalanceServiceImpl implements BalanceService
{
  @Autowired
  private BalanceRepository balanceRepository;

  @PersistenceContext
  private EntityManager entityManager;

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
      throw new BalanceNotFoundException("Balance not found");

    balanceRepository.delete(balance.get());
  }

  @Override
  public List<Balance> getBalanceForPeriod(FilterModel filter)
  {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Balance> query = cb.createQuery(Balance.class);
    Root<Balance> balance = query.from(Balance.class);

    query.select(balance);

    if (filter.hasFrom() || filter.hasTo())
    {
      Path<Date> balanceDate = balance.get("createDate");

      ArrayList<Predicate> predicates = new ArrayList<Predicate>();

      if (filter.hasFrom())
      {
        predicates.add(cb.greaterThan(balanceDate, filter.getFrom()));
      }

      if (filter.hasTo())
      {
        predicates.add(cb.lessThan(balanceDate, filter.getTo()));
      }

      query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
    }

    return entityManager.createQuery(query)
      .getResultList();
  }

  @Override
  public List<Balance> listBalances()
  {
    return (List<Balance>) balanceRepository.findAll();
  }
}
