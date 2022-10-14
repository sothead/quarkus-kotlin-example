package org.sothea.tsubasa.domain.port.request

import arrow.core.Validated
import io.smallrye.mutiny.Uni
import org.sothea.tsubasa.domain.error.TsubasaError
import org.sothea.tsubasa.domain.model.Club

interface IRequestClubs {

  fun findClubs(): Validated<TsubasaError, Uni<Set<Club>>>

  fun initialize(): Validated<TsubasaError, Uni<Set<Club>>>

}
