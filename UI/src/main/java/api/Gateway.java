package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Gateway {
  public CompletableFuture<TokenModel> login(String username, String password) throws URISyntaxException {
    try {
      LoginModel loginModel = new LoginModel(username, password);

      HttpRequest request = preparePost(new URI("http://localhost:8080/auth/signin"), loginModel, null);

      return handleRequestWithResponseBody(request).thenApply(body -> {
        Gson gson = new Gson();

        return gson.fromJson(body, TokenModel.class);
      });
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<Void> addArticle(String name, String token) throws URISyntaxException {
    try {
      ArticleModel articleModel = new ArticleModel(name);
      HttpRequest request = preparePost(new URI("http://localhost:8080/article"), articleModel, token);

      return handleRequestWithoutResponseBody(request);
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<Void> addBalance(Date createDate, double debit, double credit, String token)
    throws URISyntaxException {
    try {
      BalanceModel balanceModel = new BalanceModel(createDate, debit, credit);
      HttpRequest request = preparePost(new URI("http://localhost:8080/balance"), balanceModel, token);

      return handleRequestWithoutResponseBody(request);
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<Void> addOperation(int articleId, double debit, double credit, Date createDate,
                                              int balanceId, String token) throws URISyntaxException {
    try {
      OperationModel operationModel = new OperationModel(articleId, debit, credit, createDate, balanceId);
      HttpRequest request = preparePost(new URI("http://localhost:8080/operation"), operationModel, token);

      return handleRequestWithoutResponseBody(request);
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<Void> delete(String uri, String id, TokenModel tokenModel) throws URISyntaxException {
    try {
      return sendDelete(new URI(uri + id), tokenModel.getToken());
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<ArrayList<ArticleModelGet>> getArticle(TokenModel tokenModel) throws URISyntaxException {
    try {
      return sendGet(new URI("http://localhost:8080/article"), tokenModel.getToken())
        .thenApply(response -> {
          Gson gson = new Gson();
          Type listType = new TypeToken<ArrayList<ArticleModelGet>>(){}.getType();

          return gson.fromJson(response, listType);
      });
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<ArrayList<BalanceModelGet>> getBalance(TokenModel tokenModel) throws URISyntaxException {
    try {
      return sendGet(new URI("http://localhost:8080/balance"), tokenModel.getToken())
        .thenApply(response -> {
          Gson gson = new Gson();
          Type listType = new TypeToken<ArrayList<BalanceModelGet>>(){}.getType();

          return gson.fromJson(response, listType);
        });
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<ArrayList<OperationModelGet>> getOperation(TokenModel tokenModel) throws URISyntaxException {
    try {
      return sendGet(new URI("http://localhost:8080/operation"), tokenModel.getToken())
        .thenApply(response -> {
          Gson gson = new Gson();
          Type listType = new TypeToken<ArrayList<OperationModelGet>>(){}.getType();

          return gson.fromJson(response, listType);
        });
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<ArrayList<OperationModelGet>> getArticleFilter(String id,
                                                                          TokenModel tokenModel) throws URISyntaxException {
    try {
      return sendGet(new URI("http://localhost:8080/article/" + id), tokenModel.getToken())
        .thenApply(response -> {
          Gson gson = new Gson();
          Type listType = new TypeToken<ArrayList<OperationModelGet>>(){}.getType();

          return gson.fromJson(response, listType);
        });
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }






  private <TBody> HttpRequest preparePost(URI uri, TBody body, String token) {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    String requestBody = gson.toJson(body);

    HttpRequest.Builder builder = getBuilder(uri, token).header("Content-Type", "application/json");

    return builder.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
  }

  private CompletableFuture<Void> sendDelete(URI uri, String token) {
    HttpRequest.Builder builder = getBuilder(uri, token);
    HttpRequest request = builder.DELETE().build();
    return handleRequestWithoutResponseBody(request);
  }

  private CompletableFuture<String> sendGet(URI uri, String token) {
    HttpRequest.Builder builder = getBuilder(uri, token);
    HttpRequest request = builder.GET().build();
    return handleRequestWithResponseBody(request);
  }

  private HttpRequest.Builder getBuilder(URI uri, String token) {
    HttpRequest.Builder builder = HttpRequest.newBuilder().
      uri(uri).header("Accept", "application/json");

    if(token != null) {
      builder = builder.header("Authorization", "Bearer " + token);
    }

    return builder;
  }

  private CompletableFuture<String> handleRequestWithResponseBody(HttpRequest request) {
    return HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
      if(response.statusCode() == 403) {
        throw new CompletionException(new AccessDeniedException("403"));
      }

      return response.body();
    });
  }

  private CompletableFuture<Void> handleRequestWithoutResponseBody(HttpRequest request) {
    return HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(response -> {
      if(response.statusCode() != 200) {
        throw new CompletionException(new AccessDeniedException("!=200"));
      }
    });
  }
}
