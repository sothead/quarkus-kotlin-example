package org.sothea.tsubasa.domain.port

import io.smallrye.mutiny.Uni
import org.sothea.tsubasa.domain.model.Player

interface IObtainPlayers {

  fun findPlayers(): Uni<Set<Player>>

}