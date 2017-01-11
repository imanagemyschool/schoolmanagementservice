package models.domain

import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 12/16/2016.
  */
case class StudentGrade(studentId: Long,
                        termCode: String,
                        subjectCode: String,
                        totalPercentage: Option[Double],
                        totalGradeMark: Option[String],
                        gradeTeacherName: Option[String],
                        lastUpdatedDate: Option[LocalDateTime],
                        createTime: LocalDateTime)