package io.fouad.bugs.bug01.dto;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

public interface User {
	
	enum Role {
		UNUSED,
		ROLE1, // 1
		ROLE2 // 2
	}
	
	Integer getId();
	String getUsername();
	Set<Role> getRoles();
	Instant getCreationTimestamp();
	Optional<Instant> getUpdateTimestamp();
}