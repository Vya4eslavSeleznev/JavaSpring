package main.service;

import main.entity.Article;
import main.entity.Operation;

import java.util.List;

public interface ArticleService
{
  void addArticle(Article article);
  void deleteArticle(int id);
  List<Article> listArticles();
  List<Operation> getCreditForCategory(int id);
}
