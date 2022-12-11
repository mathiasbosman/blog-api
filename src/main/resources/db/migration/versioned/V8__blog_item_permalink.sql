alter table blog_item
    add permalink varchar(2048);

update blog_item
set permalink = id;

alter table blog_item
    alter column permalink set not null;

alter table blog_item
    add unique (permalink);