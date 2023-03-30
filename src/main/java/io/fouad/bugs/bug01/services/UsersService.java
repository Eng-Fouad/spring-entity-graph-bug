package io.fouad.bugs.bug01.services;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import io.fouad.bugs.bug01.dao.UsersRepository;
import io.fouad.bugs.bug01.dto.User.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class UsersService {
	
	private final UsersRepository usersRepository;
	
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Set<Role> getUserRolesById(int id) {
		return usersRepository.queryFirstById(id)
							  .orElseThrow()
							  .getRoles();
	}
}