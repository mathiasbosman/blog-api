alter table blog_item
    alter column excerpt set not null;

alter table blog_item
    add constraint blog_item_blog_user_id_fk
        foreign key (poster_id) references blog_user;

alter table blog_item
    add featured boolean;