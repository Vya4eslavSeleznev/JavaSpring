package main.entity;

import javax.persistence.*;

@Entity
public class Application
{
  public Application()
  {
  }

  public Application(String name, String description)
  {
    this.name = name;
    this.description = description;
  }

  public Integer getId ()
  {
    return id;
  }

  public void setId (Integer id)
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

  public String getDescription ()
  {
    return description;
  }

  public void setDescription (String description)
  {
    this.description = description;
  }

  @Override
  public String toString ()
  {
    return "Application{" +
      "id=" + id +
      ", name=" + name + '\'' +
      ", description='" + description + '\'' +
      '}';
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @Column(name = "app_name", nullable = false)
  private String name;

  private String description;
}
