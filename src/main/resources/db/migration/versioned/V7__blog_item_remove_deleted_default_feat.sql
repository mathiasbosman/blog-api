alter table blog_item
drop
column deleted;

alter table blog_item
    alter column featured set default false;

