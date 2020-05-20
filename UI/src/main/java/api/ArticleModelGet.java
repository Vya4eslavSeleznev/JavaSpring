package api;

public class ArticleModelGet {
  private int id;
  private String name;

  public ArticleModelGet(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
