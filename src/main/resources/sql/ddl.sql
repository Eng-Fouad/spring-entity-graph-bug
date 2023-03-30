CREATE DATABASE mydb;
USE mydb;

CREATE TABLE users (
    id INT NOT NULL IDENTITY,
    username VARCHAR(50) NOT NULL,
    creation_timestamp DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    update_timestamp DATETIME2,
    
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE roles (
    id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    creation_timestamp DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    
    CONSTRAINT pk_roles PRIMARY KEY (id),
    CONSTRAINT uq_roles_name UNIQUE (name)
);

CREATE TABLE users_roles_mapping (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    creation_timestamp DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    
    CONSTRAINT pk_users_roles_mapping PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_users_roles_mapping_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_users_roles_mapping_role FOREIGN KEY (role_id) REFERENCES roles (id)
);