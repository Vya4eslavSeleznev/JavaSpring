package main.model;

import java.util.Date;
import java.util.Optional;

public class FilterModel
{
  private Optional<Date> to;
  private Optional<Date> from;

  public FilterModel(Optional<Date> to, Optional<Date> from)
  {
    this.to = to;
    this.from = from;
  }

  public boolean hasTo() { return to.isPresent(); }

  public Date getTo()
  {
    return to.get();
  }

  public boolean hasFrom() { return from.isPresent(); }

  public Date getFrom()
  {
    return from.get();
  }
}
