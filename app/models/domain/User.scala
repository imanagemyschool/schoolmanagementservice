package models.domain

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty, JsonIgnoreProperties}
import org.joda.time.LocalDateTime

/**
  * Created by anil.mathew on 11/14/2016.
  */
case class User(userId: Long, username: String, password: String, activeFlag: Boolean, createTime: LocalDateTime)

