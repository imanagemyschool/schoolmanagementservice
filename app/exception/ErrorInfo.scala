package exception

import play.api.libs.json.Json

/**
  * Created by anil.mathew on 2/17/2016.
  */
object ErrorInfo {
    def apply(errorCode: String, description: String): ErrorInfo = new ErrorInfo {
        def code = errorCode
        def message = description
    }
}

trait ErrorInfo {
    def code: String
    def message: String

    def toJson = Json.obj("errorInfo" -> {Json.obj(
        "code" -> code,
        "description" -> message
    )})
}
