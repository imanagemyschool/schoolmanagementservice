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

    // Define the StudentCategoryGrade Table
    class StudentCategoryGradeTable(tag: Tag) extends Table[StudentCategoryGrade](tag, "studentcategorygrade") {
        def studentCategoryGradeId  = column[Long]("StudentCategoryGradeId", O.PrimaryKey)
        def studentId               = column[Long]("StudentId")
        def termCode                = column[String]("TermCode")
        def subjectCode             = column[String]("SubjectCode")
        def subjectCategoryCode     = column[String]("SubjectCategoryCode")
        def categoryItemDescription = column[Option[String]]("CategoryItemDescription")
        def categoryItemType        = column[Option[String]]("CategoryItemType")
        def categoryItemScore       = column[Option[String]]("CategoryItemScore")
        def categoryItemPercentage  = column[Option[Double]]("CategoryItemPercentage")
        def createTime              = column[LocalDateTime]("CreateTime")

        override def * = (studentCategoryGradeId, studentId, termCode, subjectCode, subjectCategoryCode, categoryItemDescription, categoryItemType, categoryItemScore, categoryItemPercentage, createTime) <> (StudentCategoryGrade.tupled, StudentCategoryGrade.unapply)
    }

    // Define the Term Table
    class TermTable(tag: Tag) extends Table[Term](tag, "term") {
        def termCode         = column[String]("TermCode", O.PrimaryKey)
        def termDescription  = column[String]("TermDescription")
        def createTime       = column[LocalDateTime]("CreateTime")

        override def * = (termCode, termDescription, createTime) <> (Term.tupled, Term.unapply)
    }

    // Define the Subject Table
    class SubjectTable(tag: Tag) extends Table[Subject](tag, "subject") {
        def subjectCode         = column[String]("SubjectCode", O.PrimaryKey)
        def subjectDescription  = column[String]("SubjectDescription")
        def createTime          = column[LocalDateTime]("CreateTime")

        override def * = (subjectCode, subjectDescription, createTime) <> (Subject.tupled, Subject.unapply)
    }

    // Define the SubjectCategory Table
    class SubjectCategoryTable(tag: Tag) extends Table[SubjectCategory](tag, "subjectcategory") {
        def subjectCategoryCode         = column[String]("SubjectCategoryCode", O.PrimaryKey)
        def subjectCategoryDescription  = column[String]("SubjectCategoryDescription")
        def createTime                  = column[LocalDateTime]("CreateTime")

        override def * = (subjectCategoryCode, subjectCategoryDescription, createTime) <> (SubjectCategory.tupled, SubjectCategory.unapply)
    }

    // Define the StudentAttendance Table
    class StudentAttendanceTable(tag: Tag) extends Table[StudentAttendance](tag, "studentattendance") {
        def studentAttendanceId  = column[Long]("StudentAttendanceId", O.PrimaryKey)
        def studentId            = column[Long]("StudentId")
        def attendanceDate       = column[LocalDateTime]("AttendanceDate")
        def presentFlag          = column[Boolean]("PresentFlag")
        def excusedFlag          = column[Boolean]("ExcusedFlag")
        def unexcusedFlag        = column[Boolean]("UnexcusedFlag")
        def tardyFlag            = column[Boolean]("TardyFlag")
        def truancyFlag          = column[Boolean]("TruancyFlag")
        def suspendedFlag        = column[Boolean]("SuspendedFlag")
        def createTime           = column[LocalDateTime]("CreateTime")

        override def * = (studentAttendanceId, studentId, attendanceDate, presentFlag, excusedFlag, unexcusedFlag, tardyFlag, truancyFlag, suspendedFlag, createTime) <> (StudentAttendance.tupled, StudentAttendance.unapply)
    }

    // Define the StudentFee Table
    class StudentFeeTable(tag: Tag) extends Table[StudentFee](tag, "studentfee") {
        def studentId   = column[Long]("StudentId", O.PrimaryKey)
        def feeTypeCode = column[String]("FeeTypeCode", O.PrimaryKey)
        def createTime  = column[LocalDateTime]("CreateTime")

        override def * = (studentId, feeTypeCode, createTime) <> (StudentFee.tupled, StudentFee.unapply)
    }

    val studentData              = TableQuery[StudentTable]
    val studentAddressData       = TableQuery[StudentAddressTable]
    val studentGradeData         = TableQuery[StudentGradeTable]
    val studentCategoryGradeData = TableQuery[StudentCategoryGradeTable]
    val termData                 = TableQuery[TermTable]
    val subjectData              = TableQuery[SubjectTable]
    val subjectCategoryData      = TableQuery[SubjectCategoryTable]
    val studentAttendanceData    = TableQuery[StudentAttendanceTable]
    val studentFeeData           = TableQuery[StudentFeeTable]
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

    // Method to get all the Term data
    def getTerms(): Future[Seq[Term]] = {
        val query = for {
            term <- termData.sortBy(_.termCode.desc)
        } yield (term)
        db.run(query.result)
    }

    // Method to get all the Subject data
    def getSubjects(): Future[Seq[Subject]] = {
        val query = for {
            subject <- subjectData.sortBy(_.subjectCode.desc)
        } yield (subject)
        db.run(query.result)
    }

    // Method to get all the SubjectCategory data
    def getSubjectCategories(): Future[Seq[SubjectCategory]] = {
        val query = for {
            subjectCategory <- subjectCategoryData.sortBy(_.subjectCategoryCode.desc)
        } yield (subjectCategory)
        db.run(query.result)
    }

    // Method to get all the StudentAttendance data
    def getStudentAttendances(studentId: Long): Future[Seq[StudentAttendance]] = {
        val query = for {
            studentAttendance <- studentAttendanceData if (studentAttendance.studentId === studentId)
        } yield (studentAttendance)
        db.run(query.result)
    }

    // Method to get all the fee data
    def getStudentFees(studentId: Long): Future[Seq[StudentFee]] = {
        val query = for {
            studentFee <- studentFeeData if (studentFee.studentId === studentId)
        } yield (studentFee)
        db.run(query.result)
    }
}
