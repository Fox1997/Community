create table Comment
(
	id BIGINT not null,
	parent_id BIGINT not null,
	type int not null,
	commentator BIGINT not null,
	gmt_create BIGINT not null,
	gmt_modified BIGINT not null,
	link_count BIGINT default 0 ,
	constraint Comment_pk
		primary key (id)
);

