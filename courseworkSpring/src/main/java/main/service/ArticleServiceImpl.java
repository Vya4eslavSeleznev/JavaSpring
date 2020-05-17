package main.service;

import main.entity.Article;
import main.entity.Balance;
import main.exception.ArticleNotFoundException;
import main.exception.BalanceNotFoundException;
import main.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService
{
  @Autowired
  private ArticleRepository articleRepository;

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
}
