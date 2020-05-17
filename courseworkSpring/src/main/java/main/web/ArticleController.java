package main.web;

import main.entity.Article;
import main.exception.ArticleNotFoundException;
import main.model.ArticleCreateModel;
import main.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping ("/article")
public class ArticleController
{
  private ArticleService articleService;

  @Autowired
  public void setArticleService (ArticleService articleService)
  {
    this.articleService = articleService;
  }

  @PostMapping
  public void addArticle(@RequestBody ArticleCreateModel articleModel)
  {
    Article article = new Article(articleModel.name);
    articleService.addArticle(article);
  }

  @DeleteMapping ("{id}")
  public void deleteArticle(@PathVariable("id") int id)
  {
    try
    {
      articleService.deleteArticle(id);
    }
    catch(ArticleNotFoundException e)
    {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
    }
  }

  @GetMapping()
  public ResponseEntity<List<Article>> getAllArticles()
  {
    List<Article> list = articleService.listArticles();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }
}
