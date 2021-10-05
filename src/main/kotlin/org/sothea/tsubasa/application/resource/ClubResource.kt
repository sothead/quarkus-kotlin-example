package org.sothea.tsubasa.application.resource

import org.sothea.tsubasa.domain.service.ClubService
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("tsubasa/clubs")
class ClubResource(
  private val clubService: ClubService
) {

  @GET
  fun list() = clubService.getClubs()

  @GET
  @Path("init")
  fun initialize() = clubService.initialize()

}