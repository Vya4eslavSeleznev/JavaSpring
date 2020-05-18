package main.service;

import main.entity.Article;
import main.entity.Operation;
import main.exception.ArticleNotFoundException;
import main.repository.ArticleRepository;
import main.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService
{
  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private OperationRepository operationRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void addArticle(Article article)
  {
    articleRepository.save(article);
  }

  @Override
  public void deleteArticle(int id)
  {
    Optional<Article> article = articleRepository.findById(id);

    if (!article.isPresent())
      throw new ArticleNotFoundException("Article not found");

    articleRepository.delete(article.get());
  }

  @Override
  public List<Article> listArticles()
  {
    return (List<Article>) articleRepository.findAll();
  }

  @Override
  public List<Operation> getCreditForCategory(int id)
  {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Operation> query = cb.createQuery(Operation.class);
    Root<Operation> operation = query.from(Operation.class);
    query.select(operation);

    Path<Integer> articleId = operation.get("articleId");

    query.where(cb.equal(articleId, id));

    return entityManager.createQuery(query)
      .getResultList();
  }
}
