drop table if exists users cascade;
drop table if exists votes cascade;
create table users (
   part tinyint check (part between 0 and 1),
   team_name tinyint check (team_name between 0 and 4),
   created_at timestamp(6),
   updated_at timestamp(6),
   user_id bigint generated by default as identity,
   email varchar(255),
   name varchar(255),
   password varchar(255),
   username varchar(255),
   primary key (user_id)
);
create table votes (
   team_name tinyint check (team_name between 0 and 4),
   created_at timestamp(6),
   part_leader_id bigint,
   updated_at timestamp(6),
   vote_id bigint generated by default as identity,
   vote_user_id bigint,
   vote_type varchar(31) not null,
   primary key (vote_id)
);
alter table if exists votes
    add constraint FKdauw8dt535cafmab4fpx31fmc
    foreign key (vote_user_id)
    references users;

alter table if exists votes
    add constraint FKgy6tc649wh51obix3hitcvht0
    foreign key (part_leader_id)
    references users;