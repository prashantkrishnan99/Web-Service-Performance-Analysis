create database if not exists stats;

use stats;

drop table deltas;
drop table projects;
create table if not exists projects ( 
	id int primary key not null auto_increment
	, projectname varchar(256) not null
    , uri varchar(1024) not null
    , requestCount int not null
    , dateCreated timestamp not null default current_timestamp
);

create table if not exists deltas ( 
	id int primary key not null auto_increment
    # , projectid int
    , uri varchar(1024)
	, roundTripTime int
    , dateCreated timestamp not null default current_timestamp
    , INDEX(uri)
    # , INDEX(projectid)
    , INDEX(dateCreated)
    # , FOREIGN KEY (projectid) REFERENCES projects(id) 
);



# insert into deltas (roundTripTime) values (5);

select * from projects;
select * from deltas;

# select avg(roundTripTime) as avgMs, count('x') as records from deltas;