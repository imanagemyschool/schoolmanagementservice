package models.persistence

import models.domain._
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

    // Define the Role Table
    class RoleTable(tag: Tag) extends Table[Role](tag, "role") {
        def roleName        = column[String]("RoleName", O.PrimaryKey)
        def roleDescription = column[String]("RoleDescription")
        def createTime      = column[LocalDateTime]("CreateTime")

        override def * = (roleName, roleDescription, createTime) <> (Role.tupled, Role.unapply)
    }

    // Define the Attribute Table
    class AttributeTable(tag: Tag) extends Table[Attribute](tag, "attribute") {
        def attributeName        = column[String]("AttributeName", O.PrimaryKey)
        def attributeDescription = column[String]("AttributeDescription")
        def createTime           = column[LocalDateTime]("CreateTime")

        override def * = (attributeName, attributeDescription, createTime) <> (Attribute.tupled, Attribute.unapply)
    }

    // Define the RoleAttribute Table
    class RoleAttributeTable(tag: Tag) extends Table[RoleAttribute](tag, "roleattribute") {
        def roleName       = column[String]("RoleName", O.PrimaryKey)
        def attributeName  = column[String]("AttributeName", O.PrimaryKey)
        def createTime     = column[LocalDateTime]("CreateTime")

        override def * = (roleName, attributeName, createTime) <> (RoleAttribute.tupled, RoleAttribute.unapply)
    }

    // Define the UserRole Table
    class UserRoleTable(tag: Tag) extends Table[UserRole](tag, "userrole") {
        def userId      = column[Long]("UserId", O.PrimaryKey)
        def roleName    = column[String]("RoleName", O.PrimaryKey)
        def createTime  = column[LocalDateTime]("CreateTime")

        override def * = (userId, roleName, createTime) <> (UserRole.tupled, UserRole.unapply)
    }

    val userData       = TableQuery[UserTable]
    val userSchoolData = TableQuery[UserSchoolTable]
    val roleData       = TableQuery[RoleTable]
    val attributeData  = TableQuery[AttributeTable]
    val roleAttributeData  = TableQuery[RoleAttributeTable]
    val userRoleData   = TableQuery[UserRoleTable]
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

    // Method to get the User roles by UserId
    def getUserRoleAttributes(userId: Long): Future[Seq[RoleAttribute]] = {
        val query = for {
            (c, s) <- userRoleData.filter(_.userId === userId) join roleAttributeData on (_.roleName === _.roleName)
        } yield (s)
        db.run(query.result)
    }
}
