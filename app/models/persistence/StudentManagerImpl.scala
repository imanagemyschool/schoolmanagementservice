package models.persistence

import models.domain._
import org.joda.time.{LocalDateTime}
import slick.driver.MySQLDriver.api._

import scala.concurrent.{Future}
import com.github.tototoshi.slick.MySQLJodaSupport._

import scala.util.{Failure, Success}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by anil.mathew on 12/1/2016.
  */
object StudentManagerImpl {

    // Define the Student Table
    class StudentTable(tag: Tag) extends Table[Student](tag, "student") {
        def studentId           = column[Long]("StudentId", O.PrimaryKey)
        def userId              = column[Long]("UserId")
        def firstName           = column[String]("FirstName")
        def middleName          = column[Option[String]]("MiddleName")
        def lastName            = column[String]("LastName")
        def gender              = column[String]("Gender")
        def dateOfBirth         = column[String]("DateOfBirth")
        def schoolAdmissionDate = column[String]("SchoolAdmissionDate")
        def schoolLeavingDate   = column[Option[String]]("SchoolLeavingDate")
        def parentFullName      = column[Option[String]]("ParentFullName")
        def guardianFullName    = column[Option[String]]("GuardianFullName")
        def activeFlag          = column[Boolean]("ActiveFlag")
        def createTime          = column[LocalDateTime]("CreateTime")

        override def * = (studentId, userId, firstName, middleName, lastName, dateOfBirth, gender, schoolAdmissionDate, schoolLeavingDate, parentFullName, guardianFullName, activeFlag, createTime) <> (Student.tupled, Student.unapply)
    }

    // Define the Student Address Table
    class StudentAddressTable(tag: Tag) extends Table[StudentAddress](tag, "studentaddress") {
        def studentAddressId    = column[Long]("StudentAddressId", O.PrimaryKey)
        def studentId           = column[Long]("StudentId")
        def address1            = column[Option[String]]("Address1")
        def address2            = column[Option[String]]("Address2")
        def city                = column[Option[String]]("City")
        def subCountry          = column[Option[String]]("SubCountry")
        def stateCode           = column[Option[String]]("StateCode")
        def provinceCode        = column[Option[String]]("ProvinceCode")
        def countryCode         = column[Option[String]]("CountryCode")
        def zip                 = column[Option[String]]("Zip")
        def primaryContactPhoneNumber   = column[Option[String]]("PrimaryContactPhoneNumber")
        def secondaryContactPhoneNumber = column[Option[String]]("SecondaryContactPhoneNumber")
        def primaryEmailAddress         = column[Option[String]]("PrimaryContactEmailAddress")
        def secondaryEmailAddress       = column[Option[String]]("SecondaryContactEmailAddress")
        def addressTypeCode     = column[Option[String]]("AddressTypeCode")
        def createTime          = column[LocalDateTime]("CreateTime")

        override def * = (studentAddressId, studentId, address1, address2, city, subCountry, stateCode, provinceCode, countryCode, zip, primaryContactPhoneNumber, secondaryContactPhoneNumber, primaryEmailAddress, secondaryEmailAddress, addressTypeCode, createTime) <> (StudentAddress.tupled, StudentAddress.unapply)
    }

    class StudentGradeTable(tag: Tag) extends Table[StudentGrade](tag, "studentgrade") {
        def studentId         = column[Long]("StudentId", O.PrimaryKey)
        def termCode          = column[String]("TermCode", O.PrimaryKey)
        def subjectCode       = column[String]("SubjectCode", O.PrimaryKey)
        def totalPercentage   = column[Option[Double]]("TotalPercentage")
        def totalGradeMark    = column[Option[String]]("TotalGradeMark")
        def gradeTeacherName  = column[Option[String]]("GradeTeacherName")
        def lastUpdatedDate   = column[Option[LocalDateTime]]("LastUpdatedDate")
        def createTime        = column[LocalDateTime]("CreateTime")

        override def * = (studentId, termCode, subjectCode, totalPercentage, totalGradeMark, gradeTeacherName, lastUpdatedDate, createTime) <> (StudentGrade.tupled, StudentGrade.unapply)
    }

    // Define the SubjectCategoryGrade Table
    class StudentCategoryGradeTable(tag: Tag) extends Table[StudentCategoryGrade](tag, "studentcategorygrade") {
        def subjectCategoryGradeId  = column[Long]("SubjectCategoryGradeId", O.PrimaryKey)
        def studentId               = column[Long]("StudentId")
        def termCode                = column[String]("TermCode")
        def subjectCode             = column[String]("SubjectCode")
        def subjectCategoryCode     = column[String]("SubjectCategoryCode")
        def categoryItemDescription = column[Option[String]]("CategoryItemDescription")
        def categoryItemType        = column[Option[String]]("CategoryItemType")
        def categoryItemScore       = column[Option[String]]("CategoryItemScore")
        def categoryItemPercentage  = column[Option[Double]]("CategoryItemPercentage")
        def createTime              = column[LocalDateTime]("CreateTime")

        override def * = (subjectCategoryGradeId, studentId, termCode, subjectCode, subjectCategoryCode, categoryItemDescription, categoryItemType, categoryItemScore, categoryItemPercentage, createTime) <> (SubjectCategoryGrade.tupled, SubjectCategoryGrade.unapply)
    }

    val studentData              = TableQuery[StudentTable]
    val studentAddressData       = TableQuery[StudentAddressTable]
    val studentGradeData         = TableQuery[StudentGradeTable]
    val studentCategoryGradeData = TableQuery[StudentCategoryGradeTable]
    val db                       = Database.forConfig("slicksmservices")

    // Method to get the students by userId
    def getStudents(userId: Long): Future[Seq[Student]] = {
        val query = for {
            student <- studentData if (student.userId === userId)
        } yield (student)
        db.run(query.result)
    }

    // Method to get the student address records by userId
    def getStudentAddress(studentId: Long): Future[Seq[StudentAddress]] = {
        val query = for {
            studentAddress <- studentAddressData if (studentAddress.studentId === studentId)
        } yield (studentAddress)
        db.run(query.result)
    }

    // Method to get the student grades by studentId
    def getStudentGrades(studentId: Long): Future[Seq[StudentGrade]] = {
        val query = for {
            studentGrade <- studentGradeData if (studentGrade.studentId === studentId)
        } yield (studentGrade)
        db.run(query.result)
    }

    // Method to get the student category grades by studentId/termcode/subjectcode
    def getStudentCategoryGrades(studentId: Long, termCode: String, subjectCode: String): Future[Seq[StudentCategoryGrade]] = {
        val query = for {
            studentCategoryGrade <- studentCategoryGradeData if (studentCategoryGrade.studentId === studentId && studentCategoryGrade.termCode === termCode && studentCategoryGrade.subjectCode === subjectCode)
        } yield (studentCategoryGrade)
        db.run(query.result)
    }
}
