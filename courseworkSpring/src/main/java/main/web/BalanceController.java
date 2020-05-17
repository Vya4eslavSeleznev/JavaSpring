package main.web;

import main.entity.Article;
import main.entity.Balance;
import main.exception.BalanceNotFoundException;
import main.model.BalanceCreateModel;
import main.model.FilterModel;
import main.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/balance")
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

  @DeleteMapping("{id}")
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

  @GetMapping()
  public ResponseEntity<List<Balance>> getBalanceForPeriod(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Optional<Date> from,
                                                           @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Optional<Date> to)
  {
    FilterModel filter = new FilterModel(to, from);

    List<Balance> list = balanceService.getBalanceForPeriod(filter);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }
}
