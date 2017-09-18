DROP TABLE IF EXISTS roles;
INSERT INTO roles VALUES (1, 'ROLE_USER'),(2, 'ROLE_ADMIN');

INSERT INTO users (user_pk, email, password) VALUES (1, 'admin@gmail.com', 'admin1234');
INSERT INTO user_role (User_user_pk, roles_role_pk) VALUES (1, 2);
INSERT INTO users (user_pk, email, password) VALUES (2, 'user@gmail.com', 'user1234');
INSERT INTO user_role (User_user_pk, roles_role_pk) VALUES (2, 1);