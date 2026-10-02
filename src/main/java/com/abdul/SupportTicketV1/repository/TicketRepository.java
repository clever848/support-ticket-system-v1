package com.abdul.SupportTicketV1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abdul.SupportTicketV1.entity.Ticket;
import com.abdul.SupportTicketV1.enums.Status;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer>{
	Optional<Ticket> findByIdAndCreatedById(Integer ticketId,Integer userId);
	List<Ticket> findByStatusInAndAssignedToId(List<Status> status,Integer id);
}
