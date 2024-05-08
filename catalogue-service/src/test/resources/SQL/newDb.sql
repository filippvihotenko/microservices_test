CREATE schema if not exists catalogue;

create table catalogue.t_item(
         id  serial not null PRIMARY KEY,
         c_title varchar(50),
         c_details varchar(50)
);
