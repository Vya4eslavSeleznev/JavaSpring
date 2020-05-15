package main.service;

import main.entity.Operation;
import main.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements OperationService
{
  @Autowired
  private OperationRepository operationRepository;

  @Override
  public void addOperation(Operation operation)
  {
    operationRepository.save(operation);
  }

  @Override
  public void deleteOperation(int id)
  {
    operationRepository.deleteById(id);
  }
}
