package controllers

import models.common.LocalDateTimeFormat
import models.domain.{School, UserSchool, User, UserLoginInfo}
import models.persistence.{SchoolManagerImpl, UserManagerImpl}
import org.joda.time.LocalDateTime
import play.api.{Logger}
import play.api.libs.json.{Format, Json}
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
  * Created by anil.mathew on 11/12/2016.
  */
class LoginController extends Controller {
    implicit val jodaTimeFormat: Format[LocalDateTime] = LocalDateTimeFormat.ldtISOFormat
    implicit val userLoginInfoFormat  = Json.writes[UserLoginInfo]
    implicit val userSchoolFormat     = Json.writes[UserSchool]
    implicit val userFormat           = Json.writes[User]
    implicit val schoolFormat         = Json.writes[School]

    def getUser(username: Option[String]) = Action.async { implicit request =>
        UserManagerImpl.getUserByUsername(username.getOrElse("")).map(user => Ok(Json.obj("user" -> user)))
    }

    def getUserSchool(userId: Option[Long]) = Action.async { implicit request =>
        UserManagerImpl.getUserSchoolInfoList(userId.getOrElse(0)).map(userSchoolInfo => Ok(Json.obj("userSchoolInfoList" -> userSchoolInfo)))
    }

}
