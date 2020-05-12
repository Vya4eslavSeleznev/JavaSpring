package main.service;

import main.entity.Operation;
import main.exception.OperationNotFoundException;
import main.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService
{
  @Override
  public Operation findOperation(long id)
  {
    Optional<Operation> optionalApp = operationRepository.findById(id);

    if (optionalApp.isPresent())
    {
      return optionalApp.get();
    }
    else
    {
      throw new OperationNotFoundException("Operation not found");
    }
  }

  @Override
  public Operation addOperation (Operation operation)
  {
    return operationRepository.save(operation);
  }

  @Autowired
  private OperationRepository operationRepository;
}
