package org.sothea.tsubasa.application.resource

import org.sothea.tsubasa.domain.service.PlayerService
import java.util.logging.Logger.getLogger
import javax.ws.rs.GET
import javax.ws.rs.Path


@Path("tsubasa/characters")
class PlayerResource(
  private val playerService: PlayerService
) {

  private val logger = getLogger(PlayerResource::class.java.toString())

  @GET
  fun list() =
    logger.info("Start playerService.findPlayers()")
      .let { playerService.findPlayers() }
      .also { logger.info("log test with : $it") }

}