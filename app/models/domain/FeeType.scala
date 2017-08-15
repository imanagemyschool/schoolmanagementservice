package models.domain

import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 8/8/2017.
  */
case class FeeType(feeTypeCode: String, feeTypeDescription: String, feeAmount: String, createTime: LocalDateTime)
