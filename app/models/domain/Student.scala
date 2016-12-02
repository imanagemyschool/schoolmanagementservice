package models.domain

import org.joda.time.{LocalDateTime}

/**
  * Created by anil.mathew on 12/1/2016.
  */

case class Student (studentId: Long,
                    userId: Long,
                    firstName: String,
                    middleName: Option[String],
                    lastName: String,
                    dateOfBirth: String,
                    gender: String,
                    schoolAdmissionDate: String,
                    schoolLeavingDate: Option[String],
                    parentFullName: Option[String],
                    guardianFullName: Option[String],
                    activeFlag: Boolean,
                    createTime: LocalDateTime)