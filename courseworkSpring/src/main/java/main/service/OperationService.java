package main.service;

import main.entity.Operation;

public interface OperationService
{
  Operation findOperation(long id);
  Operation addOperation(Operation operation);
}
