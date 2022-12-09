create table blog_user
(
    id       uuid
        constraint pk_user primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    created  timestamp    not null,
    updated  timestamp    not null
);