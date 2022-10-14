package org.sothea.tsubasa.domain.service

import arrow.core.Validated
import io.smallrye.mutiny.Uni
import org.sothea.tsubasa.domain.error.TsubasaError
import org.sothea.tsubasa.domain.model.Club
import org.sothea.tsubasa.domain.port.obtain.IObtainClubs
import org.sothea.tsubasa.domain.port.request.IRequestClubs
import javax.inject.Singleton

@Singleton
class ClubService(
  private val iObtainClubs: IObtainClubs
) : IRequestClubs {

  override fun findClubs(): Validated<TsubasaError, Uni<Set<Club>>> = iObtainClubs.findClubs()

  override fun initialize(): Validated<TsubasaError, Uni<Set<Club>>> = iObtainClubs.initialize()

}
