package main.service;

import main.entity.Article;
import main.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService
{
  @Autowired
  private ArticleRepository articleRepository;

  @Override
  public List<Article> getAllArticle()
  {
    return (List<Article>) articleRepository.findAll();
  }
}
