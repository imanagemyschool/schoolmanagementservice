package exception

/**
  * Created by anil.mathew on 2/17/2016.
  */
case class ServiceException(code: String, msg: String, throwable: Option[Throwable]) extends Exception {
    def this(code: String, msg: String, throwable: Throwable) = this(code, msg, Some(throwable))
    def this(code: String, msg: String) = this(code, msg, None)

    def description = s"[$code] $msg."

    override def getCause = throwable.orNull

    override def getMessage: String = throwable.map(t => s"$description").getOrElse(description)
}
