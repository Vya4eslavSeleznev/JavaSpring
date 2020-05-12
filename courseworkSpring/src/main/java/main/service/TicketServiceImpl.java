package main.service;

import main.entity.Ticket;
import main.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService
{
  @Override
  public List<Ticket> listTickets()
  {
    return (List<Ticket>) ticketRepository.findAll();
  }

  @Autowired
  private TicketRepository ticketRepository;
}
