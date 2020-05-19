package api;

import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.AccessDeniedException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class CustomHttpClient
{
  public CompletableFuture< TokenModel > login (String username, String password) throws URISyntaxException
  {
    try
    {
      LoginModel loginModel = new LoginModel(username, password);

      return sendPost(new URI("http://localhost:8080/auth/signin"), loginModel, null).thenApply(body ->
      {
        Gson gson = new Gson();

        return gson.fromJson(body, TokenModel.class);
      });
    }
    catch (Exception e)
    {
      throw new URISyntaxException("", "");
    }
  }

  private < TBody > CompletableFuture< String > sendPost (URI uri, TBody body, String token)
  {
    Gson gson = new Gson();
    String requestBody = gson.toJson(body);

    HttpRequest.Builder builder = getBuilder(uri, token).header("Content-Type", "application/json");

    HttpRequest request = builder.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

    return handleRequest(request);
  }

  private HttpRequest.Builder getBuilder (URI uri, String token)
  {
    HttpRequest.Builder builder = HttpRequest.newBuilder().
      uri(uri).header("Accept", "application/json");

    if (token != null)
    {
      builder = builder.header("Authorization", "Bearer " + token);
    }

    return builder;
  }

  private CompletableFuture< String > handleRequest (HttpRequest request)
  {
    return HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response ->
    {
      if (response.statusCode() == 403)
      {
        throw new CompletionException(new AccessDeniedException("403"));
      }

      return response.body();
    });
  }
}
