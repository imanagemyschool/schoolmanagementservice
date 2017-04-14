package controllers

import exception.{ErrorInfo, ServiceErrorKeys, ServiceException}
import models.common.LocalDateTimeFormat
import models.domain._
import models.persistence.{UserManagerImpl}
import org.joda.time.LocalDateTime
import play.api.libs.json.{JsString, Format, Json}
import play.api.mvc.{Action, Controller}


import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
  * Created by anil.mathew on 4/14/2017.
  */
class UserController extends Controller {

    implicit val jodaTimeFormat: Format[LocalDateTime] = LocalDateTimeFormat.ldtISOFormat
    implicit val userLoginInfoFormat  = Json.writes[UserLoginInfo]
    implicit val userSchoolFormat     = Json.writes[UserSchool]
    implicit val userFormat           = Json.writes[User]
    implicit val schoolFormat         = Json.writes[School]
    implicit val roleAttributeFormat  = Json.writes[RoleAttribute]

    def getUser(username: Option[String]) = Action.async { implicit request =>
        UserManagerImpl.getUserByUsername(username.getOrElse("")).map(user => {
            Ok(Json.obj("user" -> Json.obj(
                "userId" -> user.get.userId,
                "username" -> user.get.username,
                "activeFlag" -> user.get.activeFlag,
                "createTime" -> user.get.createTime
            )))
        })
    }

    def getUserSchool(userId: Option[Long]) = Action.async { implicit request =>
        UserManagerImpl.getUserSchoolInfoList(userId.getOrElse(0)).map(userSchoolInfoList => {
            Ok(Json.obj(
                "userSchoolInfoList" -> userSchoolInfoList.map(userSchoolInfo => {Json.obj(
                    "userId" -> userSchoolInfo.userId,
                    "schoolCode" -> userSchoolInfo.schoolCode,
                    "userTypeCode" -> userSchoolInfo.userTypeCode,
                    "createTime" -> userSchoolInfo.createTime
                )})))
        })
    }

    // Password1234!
    // This method will authenticate the user based on the username/password & school code.
    // Note that a user will have different accounts for different schools.
    //http://stackoverflow.com/questions/37784456/playframework-type-mismatch-found-scala-concurrent-futureplay-api-mvc-result
    def authenticateUser = Action.async(parse.json) { implicit request =>
        import hasher.Implicits._
        val username     = (request.body \ "username").as[String]
        val password     = (request.body \ "password").as[String]
        val schoolCode   = (request.body \ "schoolCode").as[String]
        val userTypeCode = (request.body \ "userTypeCode").as[String]

        UserManagerImpl.getUserByUsername(username).flatMap(user => {
            UserManagerImpl.getUserSchool(user.get.userId, schoolCode).map(userSchool =>{
                val pwdHash = password.salt(userSchool.get.passwordSalt).sha256.hex
                if(!pwdHash.equalsIgnoreCase(user.get.password)){
                    Status(INTERNAL_SERVER_ERROR)(ErrorInfo(ServiceErrorKeys.UNABLE_TO_AUTHENTICATE_ERROR_CODE, ServiceErrorKeys.UNABLE_TO_AUTHENTICATE_ERROR_MSG).toJson)
                }else{
                    val userToken = java.util.UUID.randomUUID().toString
                    UserManagerImpl.updateUserWithToken(userToken, user.get.userId, schoolCode, userTypeCode)
                    Ok(Json.obj("user" -> Json.obj(
                        "userId" -> user.get.userId,
                        "username" -> user.get.username,
                        "token" -> userToken
                    )))
                }
            })
        })
    }

    // This API will return all the role attributes for the passed userId
    def getUserRoleAttributes(userId: Long) = Action.async { implicit request =>
        UserManagerImpl.getUserRoleAttributes(userId).map(roleAttribute => Ok(Json.obj("roleAttributes" -> roleAttribute)))
    }

}
