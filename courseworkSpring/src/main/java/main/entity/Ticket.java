package main.entity;

import javax.persistence.*;

@Entity
public class Ticket
{
  public Ticket()
  {
  }

  public Ticket(String title, String description, Application application, Release release, String status)
  {
    this.title = title;
    this.description = description;
    this.application = application;
    this.release = release;
    this.status = status;
  }

  public Application getApplication ()
  {
    return application;
  }

  public void setApplication (Application application)
  {
    this.application = application;
  }

  public Release getRelease ()
  {
    return release;
  }

  public void setRelease (Release release)
  {
    this.release = release;
  }

  public String getStatus ()
  {
    return status;
  }

  public void setStatus (String status)
  {
    this.status = status;
  }

  public Integer getId ()
  {
    return id;
  }

  public void setId (Integer id)
  {
    this.id = id;
  }

  public String getTitle ()
  {
    return title;
  }

  public void setTitle (String title)
  {
    this.title = title;
  }

  public String getDescription ()
  {
    return description;
  }

  public void setDescription (String description)
  {
    this.description = description;
  }

  @ManyToOne
  @JoinColumn(name = "id", insertable = false, updatable = false)
  private Application application;

  @ManyToOne
  @JoinTable(name = "ticket_release",
    joinColumns = @JoinColumn(name = "ticket_fk"),
    inverseJoinColumns = @JoinColumn(name = "release_fk"))
  private Release release;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String title;
  private String description;
  private String status;
}
