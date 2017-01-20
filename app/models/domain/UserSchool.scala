package models.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 11/14/2016.
  */
@JsonIgnoreProperties(Array("passwordSalt"))
case class UserSchool(userId: Long, schoolCode: String, userTypeCode: String, passwordSalt:String, userToken: Option[String], tokenCreationTime: Option[String], createTime: LocalDateTime)
