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


CREATE TABLE IF NOT EXISTS `smservices`.`UserSchoolInfo` (
  `UserId`                  Int(20)      NOT NULL,
  `SchoolCode`              VARCHAR(64)  NOT NULL,
  `UserTypeCode`            VARCHAR(64)  NOT NULL,
  `CreateTime`              DATETIME     NOT NULL,
   PRIMARY KEY (`UserId`,`SchoolCode`,`UserTypeCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `smservices`.`SchoolDistrict` (
  `DistrictCode`            VARCHAR(64)    NOT NULL,
  `DistrictName`            VARCHAR(100)   NOT NULL,
  `Address1`                VARCHAR(256)   NULL,
  `Address2`                VARCHAR(256)   NULL,
  `City`                    VARCHAR(100)   NULL,
  `SubCountry`              VARCHAR(32)    NULL,
  `StateCode`               VARCHAR(32)    NULL,
  `ProvinceCode`            VARCHAR(32)    NULL,
  `CountryCode`             VARCHAR(10)    NULL,
  `Zip`                     VARCHAR(10)    NULL,
  `PhoneNumber`             VARCHAR(24)    NULL,
  `EmailAddress`            VARCHAR(100)   NULL,
  `CreateTime`              DATETIME       NOT NULL,
   PRIMARY KEY (`DistrictCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `smservices`.`School` (
  `SchoolCode`              VARCHAR(64)   NOT NULL,
  `SchoolName`              VARCHAR(100)  NOT NULL,
  `DistrictCode`            VARCHAR(64)    NULL,
  `Address1`                VARCHAR(256)   NULL,
  `Address2`                VARCHAR(256)   NULL,
  `City`                    VARCHAR(100)   NULL,
  `SubCountry`              VARCHAR(32)    NULL,
  `StateCode`               VARCHAR(32)    NULL,
  `ProvinceCode`            VARCHAR(32)    NULL,
  `CountryCode`             VARCHAR(10)    NULL,
  `Zip`                     VARCHAR(10)    NULL,
  `PhoneNumber`             VARCHAR(24)    NULL,
  `EmailAddress`            VARCHAR(100)   NULL,
  `CreateTime`              DATETIME      NOT NULL,
   PRIMARY KEY (`SchoolCode`),
   CONSTRAINT `school_dictrictcode_fk` FOREIGN KEY (`DistrictCode`) REFERENCES `SchoolDistrict` (`DistrictCode`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

