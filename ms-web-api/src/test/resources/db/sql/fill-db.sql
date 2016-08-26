INSERT INTO role(id, name) VALUES (1, 'ROLE_ROOT');
INSERT INTO role(id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user(id, login, email, password) VALUES (1, 'admin', 'admin@admin.com', '$2a$10$dTLh8sSdqkQVpoL31tt9renepDKsFNLKwkJdjEbg5uQV2kli0C2qu');
INSERT INTO user_role(user_id, role_id) VALUES (1,1);

INSERT INTO user(id, login, email, password) VALUES (2, 'user', 'user@admin.com', '$2a$10$dTLh8sSdqkQVpoL31tt9renepDKsFNLKwkJdjEbg5uQV2kli0C2qu');
INSERT INTO user_role(user_id, role_id) VALUES (2,1);

INSERT INTO user_to_user(user_id, friend_id) VALUES (1,2);

INSERT INTO message(id, title, text, recipient, author) VALUES (1, 'hello', 'hello', 2, 1);
INSERT INTO message(id, title, text, recipient, author) VALUES (2, 'hi', 'hi', 1, 2);
INSERT INTO message(id, title, text, recipient, author) VALUES (3, 'how are you?', 'how are you?', 1, 2);
