package org.sothea.tsubasa.domain.port

import io.smallrye.mutiny.Uni
import org.sothea.tsubasa.domain.model.Club

interface IObtainClubs {

  fun findClubs(): Uni<Set<Club>>

  fun initialize(): Uni<Set<Club>>

}