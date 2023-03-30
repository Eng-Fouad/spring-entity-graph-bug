package io.fouad.bugs.bug01.dao;

import java.time.Instant;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import io.fouad.bugs.bug01.dto.User;
import io.fouad.bugs.bug01.dto.User.Role;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Table(name = "users")
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;
	
	@NotNull
	@Column(name = "username")
	String username;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "users_roles_mapping", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "role_id")
	Set<Role> roles;
	
	@CreatedDate
	@Column(name = "creation_timestamp")
	Instant creationTimestamp;
	
	@LastModifiedDate
	@Column(name = "update_timestamp", insertable = false)
	Instant updateTimestamp;
	
	public User toUser() {
		return new SpelAwareProxyProjectionFactory().createProjection(User.class, this);
	}
}