package main.web;

import main.exception.OperationNotFoundException;
import main.model.OperationCreateModel;
import main.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/operation")
public class OperationController
{
  private OperationService operationService;

  @Autowired
  public void setOperationService(OperationService operationService)
  {
    this.operationService = operationService;
  }

  @PostMapping()
  public void addOperation(@RequestBody OperationCreateModel operationModel)
  {
    operationService.addOperation(operationModel);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteOperation(@PathVariable("id") int id)
  {
    try
    {
      operationService.deleteOperation(id);
    }
    catch(OperationNotFoundException e)
    {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found");
    }
  }
}
