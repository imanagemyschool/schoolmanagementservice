package exception


/**
  * Created by anil.mathew on 2/17/2016.
  */
object ServiceErrorKeys extends ServiceErrorKeys

trait ServiceErrorKeys {

    val UNABLE_TO_AUTHENTICATE_ERROR_CODE = "SMS.1001"

    val UNABLE_TO_AUTHENTICATE_ERROR_MSG  = "We are unable to authenticate your request. Please check your username & password and try again."
}
