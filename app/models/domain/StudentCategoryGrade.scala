package models.domain

import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 1/10/2017.
  */
class StudentCategoryGrade (subjectCategoryGradeId: Long,
                            studentId: Long,
                            termCode: String,
                            subjectCode: String,
                            subjectCategoryCode: String,
                            categoryItemDescription: Option[String],
                            categoryItemType: Option[String],
                            categoryItemScore: Option[String],
                            categoryItemPercentage: Option[Double],
                            createTime: LocalDateTime)
