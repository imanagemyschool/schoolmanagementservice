package models.domain

import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 11/14/2016.
  */
case class User(userId: Long, username: String, activeFlag: Boolean, createTime: LocalDateTime)

