package models.domain

import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 11/14/2016.
  */
case class UserSchool(userId: Long, schoolCode: String, userTypeCode: String, createTime: LocalDateTime)
