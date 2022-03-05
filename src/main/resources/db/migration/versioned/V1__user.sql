create table user_account
(
    id       uuid         not null
        constraint user_account_pk
            primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    roles    text
);

create unique index user_account_username_uindex
    on user_account (username);

