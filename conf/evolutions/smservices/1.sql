# --- !Ups

CREATE TABLE IF NOT EXISTS `smservices`.`User` (
  `UserId`                  Int(20)      NOT NULL AUTO_INCREMENT,
  `Username`                VARCHAR(64)  NOT NULL,
  `Password`                VARCHAR(64)  NOT NULL,
  `ActiveFlag`              Int(1)       NOT NULL,
  `CreateTime`              DATETIME     NOT NULL,
   PRIMARY KEY (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `smservices`.`UserAttributes` (
  `UserId`                  Int(20)      NOT NULL,
  `AttributeName`           VARCHAR(64)  NOT NULL,
  `AttributeValue`          VARCHAR(64)  NOT NULL,
  `CreateTime`              DATETIME     NOT NULL,
   PRIMARY KEY (`UserId`,`AttributeName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;