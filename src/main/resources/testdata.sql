INSERT INTO roles (type)
VALUES ('ROLE_USER');
INSERT INTO roles (type)
VALUES ('ROLE_ADMIN');
INSERT INTO levels (level_description)
VALUES ('EASY');
INSERT INTO categories (description)
VALUES ('KITE SURF');
INSERT INTO categories (description)
VALUES ('category1');
INSERT INTO categories (description)
VALUES ('category2');
INSERT INTO videos (category_id, description, level_id, video_url)
VALUES (1 , 'SOME DESCRIPTION', 1 , 'http//localhost:8080/facebook');
insert into users (email, email_verified,enabled, level_id, name, password,provider, role_id, username)
values ( 'admin', true,true, 1,'admin','$2a$10$CVNpxD6KXiAU7uW8HIvikeqiuaHja4yTKULpEISfb.sg4FmXG054.','local',2,'admin');