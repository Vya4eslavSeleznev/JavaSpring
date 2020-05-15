package main.service;

import main.entity.Operation;

public interface OperationService
{
  void addOperation(Operation operation);
  void deleteOperation(int id);
}
