package main.web;

import main.entity.Balance;
import main.exception.BalanceNotFoundException;
import main.model.BalanceCreateModel;
import main.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping ("/balance")
public class BalanceController
{
  private BalanceService balanceService;

  @Autowired
  public void setBalanceService(BalanceService balanceService)
  {
    this.balanceService = balanceService;
  }

  @PostMapping()
  public void addBalance(@RequestBody BalanceCreateModel balanceModel)
  {
    Balance balance = new Balance(balanceModel.createDate, balanceModel.debit,
        balanceModel.credit, balanceModel.debit - balanceModel.credit);

    balanceService.addBalance(balance);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteBalance(@PathVariable("id") int id)
  {
    try
    {
      balanceService.deleteBalance(id);
    }
    catch(BalanceNotFoundException e)
    {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found");
    }
  }
}
