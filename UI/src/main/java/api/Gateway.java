package api;

import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Gateway {
  public CompletableFuture<TokenModel> login(String username, String password) throws URISyntaxException {
    try {
      LoginModel loginModel = new LoginModel(username, password);

      return sendPost(new URI("http://localhost:8080/auth/signin"), loginModel, null).thenApply(body -> {
        Gson gson = new Gson();

        return gson.fromJson(body, TokenModel.class);
      });
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<TokenModel> addArticle(String name, TokenModel tokenModel) throws URISyntaxException {
    try {
      ArticleModel articleModel = new ArticleModel(name);

      return sendPost(new URI("http://localhost:8080/article"), articleModel, tokenModel.getToken()).thenApply(body -> {
        Gson gson = new Gson();

        return gson.fromJson(body, TokenModel.class);
      });
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<TokenModel> deleteArticle(String name, TokenModel tokenModel) throws URISyntaxException {
    try {
      ArticleModel articleModel = new ArticleModel(name);

      return sendPost(new URI("http://localhost:8080/article"), articleModel, tokenModel.getToken()).thenApply(body -> {
        Gson gson = new Gson();

        return gson.fromJson(body, TokenModel.class);
      });
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<TokenModel> addBalance(Date createDate, double debit, double credit,
                                                  TokenModel tokenModel) throws URISyntaxException {
    try {
      BalanceModel balanceModel = new BalanceModel(createDate, debit, credit);

      return sendPost(new URI("http://localhost:8080/balance"), balanceModel, tokenModel.getToken()).thenApply(body -> {
        Gson gson = new Gson();

        return gson.fromJson(body, TokenModel.class);
      });
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  public CompletableFuture<TokenModel> addOperation(int articleId, double debit, double credit,
                                                    Date createDate, int balanceId,
                                                    TokenModel tokenModel) throws URISyntaxException {
    try {
      OperationModel operationModel = new OperationModel(articleId, debit, credit, createDate, balanceId);

      return sendPost(new URI("http://localhost:8080/operation"), operationModel, tokenModel.getToken()).thenApply(body -> {
        Gson gson = new Gson();

        return gson.fromJson(body, TokenModel.class);
      });
    }
    catch(Exception e) {
      throw new URISyntaxException("", "");
    }
  }

  private <TBody> CompletableFuture<String> sendPost(URI uri, TBody body, String token) {
    Gson gson = new Gson();
    String requestBody = gson.toJson(body);

    HttpRequest.Builder builder = getBuilder(uri, token).header("Content-Type", "application/json");
    HttpRequest request = builder.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

    return handleRequest(request);
  }

  private <TBody> CompletableFuture<String> sendDelete(URI uri, TBody body, String token) {
    Gson gson = new Gson();
    String requestBody = gson.toJson(body);

    HttpRequest.Builder builder = getBuilder(uri, token).header("Content-Type", "application/json");
    HttpRequest request = builder.DELETE().build();

    return handleRequest(request);
  }

  private HttpRequest.Builder getBuilder(URI uri, String token) {
    HttpRequest.Builder builder = HttpRequest.newBuilder().
      uri(uri).header("Accept", "application/json");

    if(token != null) {
      builder = builder.header("Authorization", "Bearer " + token);
    }

    return builder;
  }

  private CompletableFuture<String> handleRequest(HttpRequest request) {
    return HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
      if(response.statusCode() == 403) {
        throw new CompletionException(new AccessDeniedException("403"));
      }

      return response.body();
    });
  }
}
