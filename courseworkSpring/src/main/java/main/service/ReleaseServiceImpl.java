package main.service;

import main.entity.Release;
import main.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseServiceImpl implements ReleaseService
{
  @Override
  public List<Release> listRelease ()
  {
    return (List<Release>) releaseRepository.findAll();
  }

  @Autowired
  private ReleaseRepository releaseRepository;
}
