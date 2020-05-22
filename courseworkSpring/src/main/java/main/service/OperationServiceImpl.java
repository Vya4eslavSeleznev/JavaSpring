package main.service;

import main.entity.Article;
import main.entity.Balance;
import main.entity.Operation;
import main.exception.ArticleNotFoundException;
import main.exception.OperationNotFoundException;
import main.model.FilterModel;
import main.model.OperationCreateModel;
import main.repository.ArticleRepository;
import main.repository.BalanceRepository;
import main.repository.OperationRepository;
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
public class OperationServiceImpl implements OperationService {
  @Autowired
  private OperationRepository operationRepository;

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private BalanceRepository balanceRepository;

  @PersistenceContext
  private EntityManager entityManager;

  public void addOperation(OperationCreateModel operationModel) {
    Optional<Article> article = articleRepository.findById(operationModel.getArticleId());

    if(!article.isPresent()) {
      throw new ArticleNotFoundException("Article not found");
    }

    Optional<Balance> optionalBalance = balanceRepository.findById(operationModel.getBalanceId());

    if(!optionalBalance.isPresent()) {
      throw new ArticleNotFoundException("Balance not found");
    }

    Balance balance = optionalBalance.get();

    Operation operation = new Operation(article.get(), balance, operationModel.getDebit(), operationModel.getCredit(),
      operationModel.getCreateDate());

    balance.setDebit(balance.getDebit() + operation.getDebit());
    balance.setCredit(balance.getCredit() + operation.getCredit());

    balanceRepository.save(balance);
    operationRepository.save(operation);
  }

  @Override
  public void deleteOperation(int id) {
    Optional<Operation> operation = operationRepository.findById(id);

    if(!operation.isPresent())
      throw new OperationNotFoundException("Operation not found");

    Optional<Balance> optionalBalance = balanceRepository.findById(operation.get().getBalanceId());

    if(!optionalBalance.isPresent()) {
      throw new ArticleNotFoundException("Balance not found");
    }

    Balance balance = optionalBalance.get();

    balance.setDebit(balance.getDebit() - operation.get().getDebit());
    balance.setCredit(balance.getCredit() - operation.get().getCredit());

    balanceRepository.save(balance);
    operationRepository.delete(operation.get());
  }

  @Override
  public List<Operation> listOperations() {
    return (List<Operation>) operationRepository.findAll();
  }

  @Override
  public List<Operation> getOperationWithFilter(FilterModel filter) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Operation> query = cb.createQuery(Operation.class);
    Root<Operation> operation = query.from(Operation.class);

    query.select(operation);

    if(filter.hasFrom() || filter.hasTo()) {
      Path<Date> operationDate = operation.get("createDate");

      ArrayList<Predicate> predicates = new ArrayList<>();

      if(filter.hasFrom()) {
        predicates.add(cb.greaterThan(operationDate, filter.getFrom()));
      }

      if(filter.hasTo()) {
        predicates.add(cb.lessThan(operationDate, filter.getTo()));
      }

      query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
    }

    return entityManager.createQuery(query).getResultList();
  }
}
