package main.service;

import main.entity.Operation;
import main.model.FilterModel;
import main.model.OperationCreateModel;

import java.util.List;

public interface OperationService {
  void addOperation(OperationCreateModel operationModel);

  void deleteOperation(int id);

  List<Operation> listOperations();

  List<Operation> getOperationWithFilter(FilterModel filter);
}
