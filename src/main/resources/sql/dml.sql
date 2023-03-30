USE mydb;

INSERT INTO users (username) VALUES ('user1');
INSERT INTO roles (id, name) VALUES (1, 'role1'), (2, 'role2');
INSERT INTO users_roles_mapping (user_id, role_id) VALUES (1, 1), (1, 2);