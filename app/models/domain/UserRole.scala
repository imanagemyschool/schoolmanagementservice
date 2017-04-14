package models.domain

import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 4/14/2017.
  */
case class UserRole(userId: Long, roleName: String, createTime: LocalDateTime)
