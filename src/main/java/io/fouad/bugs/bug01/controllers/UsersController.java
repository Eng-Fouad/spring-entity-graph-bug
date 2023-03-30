package io.fouad.bugs.bug01.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.fouad.bugs.bug01.dto.User.Role;
import io.fouad.bugs.bug01.services.UsersService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Validated
@RestController
public class UsersController {
	
	private final UsersService usersService;
	
	@GetMapping("/user/{id}/roles")
	@ResponseStatus(HttpStatus.OK)
	public Set<Role> getUserRoles(@PathVariable("id") int id) {
		return usersService.getUserRolesById(id);
	}
}