package main.service;

import main.entity.Article;
import main.entity.Balance;
import main.entity.Operation;
import main.exception.OperationNotFoundException;
import main.model.OperationCreateModel;
import main.repository.ArticleRepository;
import main.repository.BalanceRepository;
import main.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService
{
  @Autowired
  private OperationRepository operationRepository;

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private BalanceRepository balanceRepository;

  public void addOperation(OperationCreateModel operationModel)
  {
    Optional<Article> article = articleRepository.findById(operationModel.articleId);
    Optional<Balance> balance = balanceRepository.findById(operationModel.balanceId);

    Operation operation = new Operation(article.get(), balance.get(), operationModel.debit,
        operationModel.credit, operationModel.createDate);

    operationRepository.save(operation);
  }

  @Override
  public void deleteOperation(int id)
  {
    Optional<Operation> operation = operationRepository.findById(id);

    if (!operation.isPresent())
      throw new OperationNotFoundException("Operation not found");

    operationRepository.delete(operation.get());
  }

  /*@Override
  public List<Operation> listOperations()
  {
    return (List<Operation>) operationRepository.findAll();
  }*/
}
