CREATE DATABASE db_shiro_learn;

USE db_shiro_learn;

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT,
  username VARCHAR(100),
  PASSWORD VARCHAR(100),
  password_salt VARCHAR(100),
  CONSTRAINT pk_users PRIMARY KEY(id)
) CHARSET=utf8 ENGINE=INNODB;
CREATE UNIQUE INDEX idx_users_username ON users(username);

CREATE TABLE user_roles(
  id BIGINT AUTO_INCREMENT,
  username VARCHAR(100),
  role_name VARCHAR(100),
  CONSTRAINT pk_user_roles PRIMARY KEY(id)
) CHARSET=utf8 ENGINE=INNODB;
CREATE UNIQUE INDEX idx_user_roles ON user_roles(username, role_name);

CREATE TABLE roles_permissions(
  id BIGINT AUTO_INCREMENT,
  role_name VARCHAR(100),
  permission VARCHAR(100),
  CONSTRAINT pk_roles_permissions PRIMARY KEY(id)
) CHARSET=utf8 ENGINE=INNODB;
CREATE UNIQUE INDEX idx_roles_permissions ON roles_permissions(role_name, permission);

INSERT INTO users(username,PASSWORD)VALUES('zhang','123');
INSERT INTO users(username,PASSWORD)VALUES('wang','123');