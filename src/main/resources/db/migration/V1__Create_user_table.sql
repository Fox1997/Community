create table user
(
    id           bigint auto_increment
        primary key,
    account_id   varchar(100) null,
    name         varchar(50)  null,
    token        char(36)     null,
    gmt_create   bigint(20)       null,
    gmt_modified bigint(20)       null,
    avatar_url   varchar(100) null
)
    charset = utf8;
