package main.web;

import main.entity.Operation;
import main.exception.OperationNotFoundException;
import main.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping ("/bt")
public class BugTrackerController
{
  @Autowired
  public void setOperationService (OperationService operationService)
  {
    this.operationService = operationService;
  }

  @GetMapping ("/operation/(id)")
  public ResponseEntity<Operation> getApplication(@PathVariable ("id") long id)
  {
    try
    {
      return new ResponseEntity<>(operationService.findOperation(id), HttpStatus.OK);
    }
    catch (OperationNotFoundException ex)
    {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found");
    }
  }

  @PostMapping (value = "/addOperation", consumes = "application/json", produces = "application/json")
  public Operation addOperation(@RequestBody Operation newOperation)
  {
    return operationService.addOperation(newOperation);
  }

  private OperationService operationService;
}
