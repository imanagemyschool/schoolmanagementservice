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

        override def * = (studentId, userId, firstName, middleName, lastName, gender, dateOfBirth, schoolAdmissionDate, schoolLeavingDate, parentFullName, guardianFullName, activeFlag, createTime) <> (Student.tupled, Student.unapply)
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
        def primaryEmailAddress         = column[Option[String]]("PrimaryEmailAddress")
        def secondaryEmailAddress       = column[Option[String]]("SecondaryEmailAddress")
        def addressTypeCode     = column[Option[String]]("AddressTypeCode")
        def createTime          = column[LocalDateTime]("CreateTime")

        override def * = (studentAddressId, studentId, address1, address2, city, subCountry, stateCode, provinceCode, countryCode, zip, primaryContactPhoneNumber, secondaryContactPhoneNumber, primaryEmailAddress, secondaryEmailAddress, addressTypeCode, createTime) <> (StudentAddress.tupled, StudentAddress.unapply)
    }

    val studentData        = TableQuery[StudentTable]
    val studentAddressData = TableQuery[StudentAddressTable]
    val db                 = Database.forConfig("slicksmservices")

    // Method to get the students by userId
    def getStudents(userId: Long): Future[Seq[Student]] = {
        val query = for {
            student <- studentData if (student.userId === userId)
        } yield (student)
        db.run(query.result)
    }
}
