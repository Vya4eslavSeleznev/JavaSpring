package main.service;

import main.entity.Article;

import java.util.List;

public interface ArticleService
{
  void addArticle(Article article);
  void deleteArticle(int id);
  List<Article> listArticles();
}
