package main.web;

import main.entity.Article;
import main.entity.Balance;
import main.entity.Operation;
import main.exception.ArticleNotFoundException;
import main.model.ArticleCreateModel;
import main.model.FilterModel;
import main.service.ArticleService;
import main.service.OperationService;
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
  private OperationService operationService;

  @Autowired
  public void setArticleService (ArticleService articleService)
  {
    this.articleService = articleService;
  }

  @Autowired
  public void setOperationService (OperationService operationService)
  {
    this.operationService = operationService;
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

  @GetMapping("{id}")
  public ResponseEntity<List<Operation>> getCreditForCategory(@PathVariable("id") int id)
  {
    List<Operation> list = articleService.getCreditForCategory(id);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }
}
