package controllers


import play.api.{Play, Logger}
import play.api.libs.json.{JsString, Json}
import play.api.mvc.{Action, Controller}

/**
  * Created by anil.mathew on 11/7/2016.
  */
class HomeController extends Controller{

    def about = Action {
        Logger.debug("The application is checking for about and it is passed!")
        Ok(Json.obj(
            "name" -> "SchoolManagementService",
            "version" -> JsString(Play.current.configuration.getString("app.version").getOrElse("unknown")),
            "buildDate" -> JsString(Play.current.configuration.getString("app.builddatetime").getOrElse("unknown")),
            "apiVersions" -> Json.arr(1)
        ))
    }
}
