package main.entity;

import javax.persistence.*;

@Entity
public class Article
{
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id;

  @Column (nullable = false)
  private String name;

  public Article ()
  {
  }

  public Article (String name)
  {
    this.name = name;
  }

  public int getId ()
  {
    return id;
  }

  public void setId (int id)
  {
    this.id = id;
  }

  public String getName ()
  {
    return name;
  }

  public void setName (String name)
  {
    this.name = name;
  }
}
