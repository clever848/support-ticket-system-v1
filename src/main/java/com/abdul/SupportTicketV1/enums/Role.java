package com.abdul.SupportTicketV1.enums;

import java.util.Set;

public enum Role {
	ROLE_USER(Set.of(Permission.GET_TICKET,
					  Permission.POST_TICKET,
					  Permission.PATCH_TICKET,
					  Permission.DELETE_TICKET)),
	ROLE_ADMIN(Set.of(Permission.GET_TICKET,
					  Permission.POST_TICKET,
					  Permission.PATCH_TICKET,
					  Permission.DELETE_TICKET,
					  Permission.PUT_TICKET,
					  Permission.ASSIGN_TICKET,
					  Permission.CHANGE_ROLE_SUPPORT,
					  Permission.DELETE_USER,
					  Permission.GET_USER,
					  Permission.PATCH_USER,
					  Permission.POST_USER,
					  Permission.PUT_USER,
					  Permission.RESOLVE_TICKET)),
	ROLE_SUPER_ADMIN(Set.of(Permission.GET_TICKET,
			  Permission.POST_TICKET,
			  Permission.PATCH_TICKET,
			  Permission.DELETE_TICKET,
			  Permission.PUT_TICKET,
			  Permission.ASSIGN_TICKET,
			  Permission.CHANGE_ROLE_SUPPORT,
			  Permission.CHANGE_ROLE_ADMIN,
			  Permission.DELETE_USER,
			  Permission.GET_USER,
			  Permission.PATCH_USER,
			  Permission.POST_USER,
			  Permission.PUT_USER,
			  Permission.RESOLVE_TICKET)),
	ROLE_SUPPORT(Set.of(Permission.GET_TICKET,
						Permission.ASSIGN_TICKET,
						Permission.DELETE_TICKET,
						Permission.PATCH_TICKET,
						Permission.PUT_TICKET,
						Permission.RESOLVE_TICKET));
	private final Set<Permission> permissions;
	
	Role(Set<Permission> permissions) {
		this.permissions=permissions;
	}
	public Set<Permission> getPermissions()
	{
		return permissions;
	}
}
