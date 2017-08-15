package models.domain

import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 8/8/2017.
  */
case class StudentFee(studentId: Long, feeTypeCode: String, createTime: LocalDateTime)
