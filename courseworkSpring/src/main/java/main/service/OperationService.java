package main.service;

import main.entity.Operation;
import main.model.OperationCreateModel;

public interface OperationService
{
  void addOperation(OperationCreateModel operationModel);
  void deleteOperation(int id);
}
