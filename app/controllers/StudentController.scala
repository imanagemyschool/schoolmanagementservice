package controllers

import models.common.LocalDateTimeFormat
import models.domain.Student
import models.persistence.StudentManagerImpl
import org.joda.time.LocalDateTime
import play.api.libs.json.{Format, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by anil.mathew on 12/1/2016.
  */
class StudentController extends Controller{


    implicit val jodaTimeFormat: Format[LocalDateTime] = LocalDateTimeFormat.ldtISOFormat
    implicit val studentFormat = Json.writes[Student]

    def getStudents(userId: Long) = Action.async { implicit request =>
        StudentManagerImpl.getStudents(userId).map(student => Ok(Json.obj("students" -> student)))
    }

}
