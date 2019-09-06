insert into api_user (email, password, enabled, last_update, creation) values ('info@davinci.bz.it', '$2a$10$DKKUcKqQlNk.LaQLAeBZiejD2eG03vG86XeLuIwYKWeInN.Gs1nvm', true, now(), now());
insert into user_role (email, role) values ('info@davinci.bz.it', 'ROLE_ADMIN');
