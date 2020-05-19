package main.repository;

import main.entity.Balance;
import org.springframework.data.repository.CrudRepository;

public interface BalanceRepository extends CrudRepository<Balance, Integer> {
}
