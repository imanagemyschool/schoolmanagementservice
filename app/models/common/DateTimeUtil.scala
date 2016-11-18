package models.common

import org.joda.time.{LocalDateTime, DateTimeZone, DateTime}
import org.joda.time.format.{ISODateTimeFormat, DateTimeFormatter, DateTimeFormat}
import play.api.Logger

/**
  * Created by anil.mathew on 3/3/2016.
  */
object DateTimeUtil {

    val simpleInputDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val ISOInputDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss")
    val longDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm a") //2016-06-30 09:30 AM

    def getFormattedDateTime(dateTime: LocalDateTime): String = getFormattedDateTime(dateTime, simpleInputDateFormat)
    def getISOFormattedDateTime(dateTime: LocalDateTime): String = getFormattedDateTime(dateTime, ISOInputDateFormat)
    def getFormattedDateTime(dateTime: LocalDateTime, dateTimeFormat: DateTimeFormatter): String = dateTime.toDateTime(DateTimeZone.UTC).toString(dateTimeFormat)

    def getFormattedDateTime(dateTime: Option[LocalDateTime], dateTimeFormat: DateTimeFormatter): String = {
        dateTime.map(dateT => {
            dateT.toDateTime(DateTimeZone.UTC).toString(dateTimeFormat)
        }).getOrElse("")
    }

    def getLongFormattedDateTime(dateTime: Option[String]): String = {
        dateTime match{
            case Some(dt) => {
                val parsedDateTime = new DateTime(dt)
                val formattedTime = parsedDateTime.toDateTime(DateTimeZone.UTC).toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm a"))
                formattedTime
            }
            case None => ""
        }
    }

    def parseDateTime(formattedDateTime: String): DateTime = simpleInputDateFormat.parseDateTime(formattedDateTime).withZoneRetainFields(DateTimeZone.UTC)

    def getDateTimeInMillis(formattedDateTime: String): Long = {
        val dateTime   = simpleInputDateFormat.parseDateTime(formattedDateTime).withZoneRetainFields(DateTimeZone.UTC)
        val dtInMillis = dateTime.getMillis
        dtInMillis
    }

    def getDateTimeInMillis(formattedDateTime: Option[String], dateTimeFormat: DateTimeFormatter): Long = {
        formattedDateTime match{
            case Some(dt) => {
                var dtInMillis : Long = 0
                try {
                    val dateTime   = dateTimeFormat.parseDateTime(dt).withZoneRetainFields(DateTimeZone.UTC)
                    dtInMillis = dateTime.getMillis
                }catch{
                    case e: Throwable => {
                        Logger.error("Error while getting the getDateTimeInMillis")
                    }
                }
                dtInMillis
            }
            case None => {
                val dtInMillis : Long = 0
                dtInMillis
            }
        }

    }

    def getISOFormattedUTCDateTime(formattedDateTime: Option[String]): Option[String] = {
        formattedDateTime match{
            case Some(dt) => {
                var isoFormattedTime = ""
                try {
                    val dateTime         = longDateFormat.parseDateTime(dt).withZoneRetainFields(DateTimeZone.forID("America/Los_Angeles"))
                    isoFormattedTime = dateTime.toDateTime(DateTimeZone.UTC).toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                }catch{
                    case e: Throwable => {
                        Logger.error("Error while getting the ISO Formatted UTC DateTime")
                    }
                }
                Some(isoFormattedTime)
            }
            case None => Some("")
        }
    }
}
