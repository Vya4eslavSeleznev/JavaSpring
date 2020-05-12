package main.service;

import main.entity.Application;
import java.util.List;

public interface ApplicationService
{
  List< Application > listApplications();
  Application findApplication(long id);
  Application addApplication(Application application);
}
