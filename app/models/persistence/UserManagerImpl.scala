package models.persistence

import models.domain.{UserLoginInfo, School, UserSchool, User}
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
object UserManagerImpl {

    // Define the User Table
    class UserTable(tag: Tag) extends Table[User](tag, "user") {
        def userId      = column[Long]("UserId", O.PrimaryKey)
        def username    = column[String]("Username")
        def password    = column[String]("Password")
        def activeFlag  = column[Boolean]("ActiveFlag")
        def createTime  = column[LocalDateTime]("CreateTime")

        override def * = (userId, username, password, activeFlag, createTime) <> (User.tupled, User.unapply)
    }

    // Define the UserSchool Table
    class UserSchoolTable(tag: Tag) extends Table[UserSchool](tag, "userschoolinfo") {
        def userId       = column[Long]("UserId", O.PrimaryKey)
        def schoolCode   = column[String]("SchoolCode", O.PrimaryKey)
        def userTypeCode = column[String]("UserTypeCode", O.PrimaryKey)
        def passwordSalt = column[String]("PasswordSalt")
        def userToken    = column[Option[String]]("UserToken")
        def tokenCreationTime = column[Option[String]]("TokenCreationTime")
        def createTime   = column[LocalDateTime]("CreateTime")

        override def * = (userId, schoolCode, userTypeCode, passwordSalt, userToken, tokenCreationTime, createTime) <> (UserSchool.tupled, UserSchool.unapply)
    }

    val userData       = TableQuery[UserTable]
    val userSchoolData = TableQuery[UserSchoolTable]
    val db             = Database.forConfig("slicksmservices")

    // Method to get the User record by username
    def getUserByUsername(username: String): Future[Option[User]] = {
        val query = userData.filter(user => user.username === username)
        val action = query.result.headOption
        db.run(action)
    }

    // Method to get the UserSchool Info for the passed userId
    def getUserSchoolInfoList(userId: Long): Future[Seq[UserSchool]] = {
        val query = for {
            userSchool <- userSchoolData if (userSchool.userId === userId)
        } yield (userSchool)
        db.run(query.result)
    }

    // Method to get the User record by username
    def getUserSchool(userId: Long, schoolCode: String): Future[Option[UserSchool]] = {
        val query = userSchoolData.filter(userSchool => userSchool.userId === userId && userSchool.schoolCode === schoolCode)
        val action = query.result.headOption
        db.run(action)
    }

    // This method will set the user token & token creation time for the logged in user
    def updateUserWithToken(userToken: String, userId: Long, schoolCode: String, userTypeCode: String) = {
        val q = for { c <- userSchoolData if c.userId === userId && c.schoolCode === schoolCode && c.userTypeCode === userTypeCode} yield (c.userToken.get, c.tokenCreationTime.get)
        db.run(q.update(userToken, System.currentTimeMillis().toString))
    }

}
