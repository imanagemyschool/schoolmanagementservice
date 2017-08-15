package controllers

import play.api.mvc.Controller

import models.common.LocalDateTimeFormat
import models.domain._
import models.persistence.{SchoolManagerImpl, UserManagerImpl}
import org.joda.time.LocalDateTime
import play.api.{Logger}
import play.api.libs.json.{Format, Json}
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext


/**
  * Created by anil.mathew on 11/18/2016.
  */
class SchoolController extends Controller{

    implicit val jodaTimeFormat: Format[LocalDateTime] = LocalDateTimeFormat.ldtISOFormat
    implicit val schoolFormat   = Json.writes[School]
    implicit val feeTypeFormat  = Json.writes[FeeType]

    def getSchool(schoolCode: String) = Action.async { implicit request =>
        SchoolManagerImpl.getSchool(schoolCode).map(school => Ok(Json.obj("school" -> school)))
    }

    def getFeeType(feeTypeCode: String) = Action.async { implicit request =>
        SchoolManagerImpl.getFeeType(feeTypeCode).map(feeType => Ok(Json.obj("feeType" -> feeType)))
    }
}
