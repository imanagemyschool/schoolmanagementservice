package models.domain

import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 11/14/2016.
  */
case class School(schoolCode: String,
                  schoolName: String,
                  districtCode: Option[String],
                  address1: Option[String],
                  address2: Option[String],
                  city: Option[String],
                  subCountry: Option[String],
                  stateCode: Option[String],
                  provinceCode: Option[String],
                  countryCode: Option[String],
                  zip: Option[String],
                  phoneNumber: Option[String],
                  emailAddress: Option[String],
                  createTime: LocalDateTime)

