package com.abdul.SupportTicketV1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abdul.SupportTicketV1.entity.User;
import com.abdul.SupportTicketV1.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	Optional<User> findByIdAndRole(Integer id,Role role);
}
