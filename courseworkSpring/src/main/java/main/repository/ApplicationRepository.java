package main.repository;

import main.entity.Application;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository< Application, Long>
{
}
