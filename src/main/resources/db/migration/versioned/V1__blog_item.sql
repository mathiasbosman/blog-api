create table blog_item
(
    id        uuid
        constraint pk_blog_items primary key,
    title     varchar(255) not null,
    content   text         not null,
    poster_id uuid         not null,
    deleted   boolean,
    created   timestamp    not null,
    updated   timestamp    not null
);