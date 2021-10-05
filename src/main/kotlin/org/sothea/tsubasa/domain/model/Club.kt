package org.sothea.tsubasa.domain.model

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("clubs")
data class Club(
  val name: String,
  val players: Set<Player> = emptySet(),
)