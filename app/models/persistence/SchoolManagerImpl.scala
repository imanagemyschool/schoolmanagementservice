package models.persistence

import models.domain.{School, UserSchool, User}
import org.joda.time.{LocalDateTime}
import play.api.Logger
import slick.driver.MySQLDriver.api._

import scala.concurrent.{Future}
import com.github.tototoshi.slick.MySQLJodaSupport._

import scala.util.{Failure, Success}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by anil.mathew on 11/14/2016.
  */
object SchoolManagerImpl {

    // Define the School Table
    class SchoolTable(tag: Tag) extends Table[School](tag, "school") {
        def schoolCode   = column[String]("SchoolCode", O.PrimaryKey)
        def schoolName   = column[String]("SchoolName")
        def districtCode = column[Option[String]]("DistrictCode")
        def address1     = column[Option[String]]("Address1")
        def address2     = column[Option[String]]("Address2")
        def city         = column[Option[String]]("City")
        def subCountry   = column[Option[String]]("SubCountry")
        def stateCode    = column[Option[String]]("StateCode")
        def provinceCode = column[Option[String]]("ProvinceCode")
        def countryCode  = column[Option[String]]("CountryCode")
        def zip          = column[Option[String]]("Zip")
        def phoneNumber  = column[Option[String]]("PhoneNumber")
        def emailAddress = column[Option[String]]("EmailAddress")
        def createTime   = column[LocalDateTime]("CreateTime")

        override def * = (schoolCode, schoolName, districtCode, address1, address2, city, subCountry, stateCode, provinceCode, countryCode, zip, phoneNumber, emailAddress, createTime) <> (School.tupled, School.unapply)
    }

    val schoolData     = TableQuery[SchoolTable]
    val db             = Database.forConfig("slicksmservices")

    // Method to get the User record by username
    def getSchool(schoolCode: String): Future[Option[School]] = {
        val query = schoolData.filter(school => school.schoolCode === schoolCode)
        val action = query.result.headOption
        db.run(action)
    }

}
