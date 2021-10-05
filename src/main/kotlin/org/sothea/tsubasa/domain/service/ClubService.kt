package org.sothea.tsubasa.domain.service

import org.sothea.tsubasa.domain.port.IObtainClubs
import javax.inject.Singleton

@Singleton
class ClubService(
  private val iObtainClubs: IObtainClubs
) {

  fun getClubs() = iObtainClubs.findClubs()

  fun initialize() = iObtainClubs.initialize()

}