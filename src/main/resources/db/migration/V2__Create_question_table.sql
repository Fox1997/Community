create table question(
                         id  bigint(20) primary key not null auto_increment,
                         title varchar(30),
                         description varchar(256),
                         gmt_create bigint(20),
                         gmt_modified bigint(20),
                         creator bigint(20),
                         comment_count int(11) default 0,
                         review_count int(11) default 0,
                         link_count int(11) default 0,
                         tag varchar(256)
)