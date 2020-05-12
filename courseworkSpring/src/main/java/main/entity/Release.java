package main.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Release
{
  public Release()
  {
  }

  public Release(Integer id, String releaseDate)
  {
    this.id = id;
    this.releaseDate = releaseDate;
  }

  public Integer getId ()
  {
    return id;
  }

  public void setId (Integer id)
  {
    this.id = id;
  }

  public String getReleaseDate ()
  {
    return releaseDate;
  }

  public void setReleaseDate (String releaseDate)
  {
    this.releaseDate = releaseDate;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String releaseDate;
}
