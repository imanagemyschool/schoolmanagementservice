package controllers

import models.common.LocalDateTimeFormat
import models.domain.{StudentAddress, Student}
import models.persistence.StudentManagerImpl
import org.joda.time.LocalDateTime
import play.api.libs.json.{Format, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Right

/**
  * Created by anil.mathew on 12/1/2016.
  */
class StudentController extends Controller{


    implicit val jodaTimeFormat: Format[LocalDateTime] = LocalDateTimeFormat.ldtISOFormat
    implicit val studentFormat        = Json.writes[Student]
    implicit val studentAddressFormat = Json.writes[StudentAddress]

    def getStudents(userId: Long) = Action.async { implicit request =>
        StudentManagerImpl.getStudents(userId).map(student => Ok(Json.obj("students" -> student)))
    }

    def getStudentAddress(studentId: Long) = Action.async { implicit request =>
        StudentManagerImpl.getStudentAddress(studentId).map(studentAddress => Ok(Json.obj("studentAddress" -> studentAddress)))
    }
}
