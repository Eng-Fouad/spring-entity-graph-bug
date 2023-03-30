package io.fouad.bugs.bug01.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.fouad.bugs.bug01.dto.User;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Integer> {
	@EntityGraph(attributePaths = {"roles"})
	Optional<User> queryFirstById(int id);
}