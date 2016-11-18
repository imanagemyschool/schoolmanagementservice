package models.common

import models.common.DateTimeUtil
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter, ISODateTimeFormat}
import org.joda.time.{DateTimeZone, LocalDateTime}
import play.api.data.validation.ValidationError
import play.api.libs.json._

/**
  * Created by anil.mathew on 2/11/2016.
  */
object LocalDateTimeFormat {

    def ldtISOFormat: Format[LocalDateTime] = Format(jodaLocalDateTimeReads, jodaISODateTimeWrites)

    def jodaLocalDateTimeReads: Reads[LocalDateTime] = jodaLocalDateTimeReads(None)

    def jodaLocalDateTimeReads(pattern: String): Reads[LocalDateTime] = jodaLocalDateTimeReads(DateTimeFormat.forPattern(pattern))

    def jodaLocalDateTimeReads(formatter: DateTimeFormatter): Reads[LocalDateTime] = jodaLocalDateTimeReads(Some(formatter))

    /**
      * Copied from 'play.api.libs.json' and adjusted for LocalDateTime format
      */
    def jodaLocalDateTimeReads(formatter: Option[DateTimeFormatter]): Reads[LocalDateTime] = new Reads[LocalDateTime] {

        import org.joda.time.format.ISODateTimeFormat

        val df = if (formatter.isDefined) formatter.get else ISODateTimeFormat.dateTimeParser()

        def reads(json: JsValue): JsResult[LocalDateTime] = json match {
            case JsNumber(d) => JsSuccess(new LocalDateTime(d.toLong))
            case JsString(s) => parseDate(s) match {
                case Some(d) => JsSuccess(d)
                case None => JsError(Seq(JsPath() -> Seq(ValidationError(s"Expected datetime in format '${if (formatter.isDefined) formatter.get else "ISO"}', found: ${json.toString()}"))))
            }
            case _ => JsError(Seq(JsPath() -> Seq(ValidationError("error.expected.date"))))
        }

        //always parse date in ISO format
        private def parseDate(input: String): Option[LocalDateTime] = {
            scala.util.control.Exception.allCatch[LocalDateTime] opt df.parseDateTime(input).withZone(DateTimeZone.UTC).toLocalDateTime
        }
    }

    def jodaISODateTimeWrites: Writes[LocalDateTime] = new Writes[LocalDateTime] {
        def writes(d: org.joda.time.LocalDateTime): JsValue = JsString(DateTimeUtil.getFormattedDateTime(d, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS")))
    }

    /**
      * Copied from 'play.api.libs.json' and adjusted for LocalDateTime format
      * Note - default 'toString' implementation writes in ISO format, therefore - no explicit formatter parameter used
      */
    //LocalDateTime doesn't writes it's timezone (UTC) by default. That is wrong for ISO8601 format
    def jodaLocalDateTimeWrites: Writes[LocalDateTime] = new Writes[LocalDateTime] {
        def writes(d: org.joda.time.LocalDateTime): JsValue = JsString(d.toString + "Z")
    }

    def jodaLocalDateTimeWrites(pattern: String): Writes[LocalDateTime] = new Writes[LocalDateTime] {
        def writes(d: org.joda.time.LocalDateTime): JsValue = JsString(d.toString(pattern))
    }

    def jodaLocalDateTimeWrites(formatter: DateTimeFormatter): Writes[LocalDateTime] = new Writes[LocalDateTime] {
        def writes(d: org.joda.time.LocalDateTime): JsValue = JsString(d.toString(formatter))
    }
}
