package org.sothea.tsubasa.domain.model

import arrow.core.None
import arrow.core.Option
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("characters")
data class Player(
  val firstName: Option<String> = None,
  val lastName: String,
  val nationality: String
)