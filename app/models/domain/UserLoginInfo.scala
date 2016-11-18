package models.domain

/**
  * Created by anil.mathew on 11/14/2016.
  */
case class UserLoginInfo(userId: Long, username: String, schoolCode: String, districtCode: Option[String], userTypeCode: String)
