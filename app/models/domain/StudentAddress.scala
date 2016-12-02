package models.domain

import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 12/1/2016.
  */
case class StudentAddress(studentAddressId: Long,
                          userId: Long,
                          address1: Option[String],
                          address2: Option[String],
                          city: Option[String],
                          subCountry: Option[String],
                          stateCode: Option[String],
                          provinceCode: Option[String],
                          countryCode: Option[String],
                          zip: Option[String],
                          primaryContactNumber: Option[String],
                          secondaryContactNumber: Option[String],
                          primaryEmailAddress: Option[String],
                          secondaryEmailAddress: Option[String],
                          addressTypeCode: Option[String],
                          createTime: LocalDateTime)
