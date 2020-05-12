package main.web;

import main.entity.Application;
import main.entity.Ticket;
import main.exception.ApplicationNotFoundException;
import main.service.ApplicationService;
import main.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bt")
public class BugTrackerController
{
  @Autowired
  public void setApplicationService (ApplicationService applicationService)
  {
    this.applicationService = applicationService;
  }

  @Autowired
  public void setTicketService (TicketService ticketService)
  {
    this.ticketService = ticketService;
  }

  @GetMapping("/applications")
  public ResponseEntity<List<Application>> getAllApplications()
  {
    List<Application> list = applicationService.listApplications();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/application/(id)")
  public ResponseEntity<Application> getApplication(@PathVariable("id") long id)
  {
    try
    {
      return new ResponseEntity<>(applicationService.findApplication(id), HttpStatus.OK);
    }
    catch (ApplicationNotFoundException ex)
    {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found");
    }
  }

  @GetMapping("/tickets")
  public ResponseEntity<List<Ticket>> getAllTickets()
  {
    List<Ticket> list = ticketService.listTickets();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping(value = "/addApplication", consumes = "application/json", produces = "application/json")
  public Application addApplication(@RequestBody Application newApp)
  {
    return applicationService.addApplication(newApp);
  }

  private ApplicationService applicationService;
  private TicketService ticketService;
}
