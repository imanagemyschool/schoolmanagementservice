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
   PRIMARY KEY (`UserId`,`AttributeName`),
   CONSTRAINT `usrattr_userid_fk` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `smservices`.`UserSchoolInfo` (
  `UserId`                  Int(20)      NOT NULL,
  `SchoolCode`              VARCHAR(64)  NOT NULL,
  `UserTypeCode`            VARCHAR(64)  NOT NULL,
  `CreateTime`              DATETIME     NOT NULL,
   PRIMARY KEY (`UserId`,`SchoolCode`,`UserTypeCode`),
   CONSTRAINT `usrschinfo_userid_fk` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`) ON DELETE NO ACTION,
   CONSTRAINT `usrschinfo_schoolcode_fk` FOREIGN KEY (`SchoolCode`) REFERENCES `School` (`SchoolCode`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `smservices`.`SchoolDistrict` (
  `DistrictCode`            VARCHAR(64)    NOT NULL,
  `DistrictName`            VARCHAR(100)   NOT NULL,
  `Address1`                VARCHAR(256),
  `Address2`                VARCHAR(256),
  `City`                    VARCHAR(100),
  `SubCountry`              VARCHAR(32),
  `StateCode`               VARCHAR(32),
  `ProvinceCode`            VARCHAR(32),
  `CountryCode`             VARCHAR(10),
  `Zip`                     VARCHAR(10),
  `PhoneNumber`             VARCHAR(24),
  `EmailAddress`            VARCHAR(100),
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
   CONSTRAINT `school_districtcode_fk` FOREIGN KEY (`DistrictCode`) REFERENCES `SchoolDistrict` (`DistrictCode`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `smservices`.`Student` (
  `StudentId`               Int(20)      NOT NULL AUTO_INCREMENT,
  `UserId`                  Int(20)      NOT NULL,
  `FirstName`               VARCHAR(50)  NOT NULL,
  `MiddleName`              VARCHAR(50),
  `LastName`                VARCHAR(50)  NOT NULL,
  `Gender`                  VARCHAR(1)   NOT NULL,
  `DateOfBirth`             VARCHAR(32)  NOT NULL,
  `SchoolAdmissionDate`     VARCHAR(32)  NOT NULL,
  `SchoolLeavingDate`       VARCHAR(32),
  `ParentFullName`          VARCHAR(100),
  `GuardianFullName`        VARCHAR(100),
  `ActiveFlag`              Int(1)       NOT NULL,
  `CreateTime`              DATETIME     NOT NULL,
   PRIMARY KEY (`StudentId`),
   CONSTRAINT `student_userid_fk` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `smservices`.`StudentAddress` (
  `StudentAddressId`        Int(20)      NOT NULL AUTO_INCREMENT,
  `StudentId`               Int(20)      NOT NULL,
  `Address1`                VARCHAR(256),
  `Address2`                VARCHAR(256),
  `City`                    VARCHAR(64),
  `SubCountry`              VARCHAR(64),
  `StateCode`               VARCHAR(64),
  `ProvinceCode`            VARCHAR(64),
  `CountryCode`             VARCHAR(10),
  `Zip`                     VARCHAR(16),
  `PrimaryContactPhoneNumber`     VARCHAR(32),
  `SecondaryContactPhoneNumber`   VARCHAR(32),
  `PrimaryContactEmailAddress`    VARCHAR(100),
  `SecondaryContactEmailAddress`  VARCHAR(100),
  `AddressTypeCode`         VARCHAR(6),
  `CreateTime`              DATETIME     NOT NULL,
   PRIMARY KEY (`StudentAddressId`),
   CONSTRAINT `studentaddr_stuid_fk` FOREIGN KEY (`StudentId`) REFERENCES `Student` (`StudentId`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `smservices`.`Subject` (
  `SubjectCode`          VARCHAR(64)    NOT NULL,
  `SubjectDescription`   VARCHAR(150)    NOT NULL,
  `CreateTime`              DATETIME     NOT NULL,
   PRIMARY KEY (`SubjectCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `smservices`.`Term` (
  `TermCode`          VARCHAR(64)    NOT NULL,
  `TermDescription`   VARCHAR(150)    NOT NULL,
  `CreateTime`        DATETIME     NOT NULL,
   PRIMARY KEY (`TermCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `smservices`.`SubjectCategory` (
  `SubjectCategoryCode`          VARCHAR(100)    NOT NULL,
  `SubjectCategoryDescription`   VARCHAR(150)    NOT NULL,
  `CreateTime`        DATETIME     NOT NULL,
   PRIMARY KEY (`SubjectCategoryCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `smservices`.`SubjectGrade` (
  `StudentId`               Int(20)      NOT NULL,
  `TermCode`                VARCHAR(100) NOT NULL,
  `SubjectCode`             VARCHAR(100) NOT NULL,
  `TotalPercentage`         DECIMAL(4,3),
  `TotalGradeMark`          VARCHAR(6),
  `GradeTeacherName`        VARCHAR(100),
  `LastUpdatedDate`         VARCHAR(64),
  `CreateTime`              DATETIME     NOT NULL,
   PRIMARY KEY (`StudentId`,`TermCode`,`SubjectCode`),
   CONSTRAINT `studentgrade_stuid_fk` FOREIGN KEY (`StudentId`) REFERENCES `Student` (`StudentId`) ON DELETE NO ACTION,
   CONSTRAINT `studentgrade_subjcode_fk` FOREIGN KEY (`SubjectCode`) REFERENCES `Subject` (`SubjectCode`) ON DELETE NO ACTION,
   CONSTRAINT `studentgrade_term_fk` FOREIGN KEY (`TermCode`) REFERENCES `Term` (`TermCode`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `smservices`.`SubjectCategoryGrade` (
  `SubjectCategoryGradeId`  Int(20)      NOT NULL AUTO_INCREMENT,
  `StudentId`               Int(20)      NOT NULL,
  `TermCode`                VARCHAR(100) NOT NULL,
  `SubjectCode`             VARCHAR(100) NOT NULL,
  `SubjectCategoryCode`     VARCHAR(100) NOT NULL,
  `CategoryItemDescription` VARCHAR(256),
  `CategoryItemType`        VARCHAR(100),
  `CategoryItemScore`       VARCHAR(32),
  `CategoryItemPercentage`  DECIMAL(4,3),
  `CreateTime`              DATETIME     NOT NULL,
   PRIMARY KEY (`SubjectCategoryGradeId`),
   CONSTRAINT `studcatgrade_stuid_fk` FOREIGN KEY (`StudentId`) REFERENCES `Student` (`StudentId`) ON DELETE NO ACTION,
   CONSTRAINT `studcatgrade_subjcode_fk` FOREIGN KEY (`SubjectCode`) REFERENCES `Subject` (`SubjectCode`) ON DELETE NO ACTION,
   CONSTRAINT `studcatgrade_term_fk` FOREIGN KEY (`TermCode`) REFERENCES `Term` (`TermCode`) ON DELETE NO ACTION,
   CONSTRAINT `studcatgrade_subjcatcode_fk` FOREIGN KEY (`SubjectCategoryCode`) REFERENCES `SubjectCategory` (`SubjectCategoryCode`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

