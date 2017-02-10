package models.domain

import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 2/9/2017.
  */
case class StudentAttendance(studentAttendanceId: Long, studentId: Long, attendanceDate: LocalDateTime, presentFlag: Boolean, excusedFlag: Boolean, unexcusedFlag: Boolean, tardyFlag: Boolean, truancyFlag: Boolean, suspendedFlag: Boolean, createTime: LocalDateTime)
