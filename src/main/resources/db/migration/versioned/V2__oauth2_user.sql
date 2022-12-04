create table oauth2_user
(
    id         uuid
        constraint pk_oauth2_user primary key,
    username   varchar(255) not null,
    externalId text         not null,
    created    timestamp    not null,
    updated    timestamp    not null
);