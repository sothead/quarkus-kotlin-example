package org.sothea.tsubasa.domain.service

import org.sothea.tsubasa.domain.port.IObtainPlayers
import javax.inject.Singleton


@Singleton
class PlayerService(
  private val iObtainPlayers: IObtainPlayers
) {

  fun findPlayers() = iObtainPlayers.findPlayers()

}