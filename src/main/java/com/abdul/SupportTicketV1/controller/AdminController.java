package com.abdul.SupportTicketV1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdul.SupportTicketV1.dto.UserResponse;
import com.abdul.SupportTicketV1.service.AdminService;

@RestController
@RequestMapping("/admin/user")
public class AdminController {
	@Autowired
	AdminService adminService;
	@PreAuthorize("hasAuthority('GET_USER')")
	@GetMapping("/all")
	public ResponseEntity<Page<UserResponse>> getAllUser(
			@RequestParam Integer pgNo,@RequestParam Integer pgSize)
	{
		return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllUser(pgNo, pgSize));
	}
	@PreAuthorize("hasAuthority('CHANGE_ROLE_SUPPORT')")
	@PatchMapping("/support/{id}")
	public ResponseEntity<UserResponse> changeRoleToSupport(@PathVariable Integer id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(adminService.changeRoleToSupport(id));
	}
	@PreAuthorize("hasAuthority('CHANGE_ROLE_ADMIN')")
	@PatchMapping("/admin/{id}")
	public ResponseEntity<UserResponse> changeRoleToAdmin(@PathVariable Integer id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(adminService.changeRoleToAdmin(id));
	}
	@PreAuthorize("hasAuthority('DELETE_USER')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String,String>> deleteUser(@PathVariable Integer id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteUser(id));
	}
}
