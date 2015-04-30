CREATE USER guest WITH PASSWORD 'guest';

select * from system_auth.users;

CREATE KEYSPACE test WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

select * from system.schema_keyspaces;

use test;

create table if not exists USER (
name varchar,
PRIMARY KEY(name)) ;