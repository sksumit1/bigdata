create user 'guest' identified by 'guest';
create database test;
use ultra;
grant all on test to 'guest' ;
grant all on test to 'guest'@'%' identified by 'guest';
grant all on test to 'guest'@'localhost' identified by 'guest';
select user,password,host from mysql.user where user='guest';
flush privileges;
CREATE TABLE IF NOT EXISTS test.USER (
	NAME VARCHAR(50) NOT NULL PRIMARY KEY
) ENGINE=InnoDB;
INSERT INTO test.USER (name) values ('sumit');
INSERT INTO test.USER (name) values ('janet');
INSERT INTO test.USER (name) values ('rock');
INSERT INTO test.USER (name) values ('alex');
commit;