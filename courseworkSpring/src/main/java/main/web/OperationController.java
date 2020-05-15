package main.web;

import main.entity.Operation;
import main.model.OperationCreateModel;
import main.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/operation")
public class OperationController
{
  private OperationService operationService;

  @Autowired
  public void setOperationService (OperationService operationService)
  {
    this.operationService = operationService;
  }

  @PostMapping()
  public void addOperation(@RequestBody OperationCreateModel operationModel)
  {
    Operation operation = new Operation(operationModel.articleId, operationModel.debit,
      operationModel.credit, operationModel.createDate, operationModel.balanceId);

    operationService.addOperation(operation);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteOperation(@PathVariable("id") int id)
  {
    operationService.deleteOperation(id);
  }
}
