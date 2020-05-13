package main.service;

import main.entity.Operation;

public interface OperationService
{
  Operation findOperation(int id);
  Operation addOperation(Operation operation);
}
