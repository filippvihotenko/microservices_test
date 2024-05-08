CREATE schema if not exists catalogue;

create table catalogue.t_item(
     id  serial not null PRIMARY KEY,
     c_title varchar(50),
     c_details varchar(50)
);

insert into catalogue.t_item (id, c_title, c_details)
values (1,'Some title', 'Some details');