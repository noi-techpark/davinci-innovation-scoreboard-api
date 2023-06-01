-- SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
--
-- SPDX-License-Identifier: AGPL-3.0-or-later

create table if not exists api_user (
    id serial primary key,
    email varchar not null unique,
    password varchar not null,
    enabled boolean not null default false,
    creation timestamp not null,
    last_update timestamp not null
);

create table if not exists user_role (
    id serial primary key,
    email varchar(255) not null,
    role varchar(50) not null,
    unique (email, role),
    foreign key (email) references api_user (email)
);

