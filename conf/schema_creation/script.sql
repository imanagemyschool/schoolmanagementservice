CREATE SCHEMA `smservices`
  DEFAULT CHARACTER SET utf8;

CREATE USER 'sms'@'%' IDENTIFIED BY 'smsdev';

GRANT ALL PRIVILEGES ON smservices.* TO 'sms'@'%';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE, ALTER, REFERENCES, CREATE, DROP, INDEX, LOCK TABLES, CREATE ROUTINE, TRIGGER, CREATE TEMPORARY TABLES, ALTER ROUTINE, SHOW VIEW ON smservices.* TO sms;

FLUSH PRIVILEGES;

----
use smservices
insert into user (username, password, activeflag, createtime) values('mathew.anil@gmail.com','Password1234!',1,sysdate())
insert into user (username, password, activeflag, createtime) values('faculty@woodbury.com','Password1234!',1,sysdate())

insert into schooldistrict(districtcode, districtname, Address1, Address2, City, SubCountry, StateCode, ProvinceCode, CountryCode, Zip, PhoneNumber, EmailAddress, CreateTime) values('IUSD', 'Irvine Unified School District', '125 Great Lawn','','Irvine','','CA','','USA','92620','(949) 936-5750','info@iusd.com', sysdate())

insert into school(schoolcode, schoolname, districtcode, Address1, Address2, City, SubCountry, StateCode, ProvinceCode, CountryCode, Zip, PhoneNumber, EmailAddress, CreateTime) values('WOODBURY_ELEM', 'Woodbury Elementary School', 'IUSD', '125 Great Lawn','','Irvine','','CA','','USA','92620','(949) 936-5750','info@woodbury.com', sysdate())

insert into userschoolinfo(userid,schoolcode,usertypecode,createtime) values(1,'WOODBURY_ELEM','Parent',sysdate())
insert into userschoolinfo(userid,schoolcode,usertypecode,createtime) values(2,'WOODBURY_ELEM','Parent',sysdate())
insert into userschoolinfo(userid,schoolcode,usertypecode,createtime) values(2,'WOODBURY_ELEM','Faculty',sysdate())
