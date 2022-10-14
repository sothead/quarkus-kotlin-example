package org.sothea.tsubasa.application.resource

import org.sothea.tsubasa.application.mapper.TsubasaErrorMapper
import org.sothea.tsubasa.domain.port.request.IRequestClubs
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("tsubasa/clubs")
class ClubResource(
  private val iRequestClubs: IRequestClubs,
  private val tsubasaErrorMapper: TsubasaErrorMapper
) {

  @GET
  fun findClubs(): Response =
    iRequestClubs.findClubs()
      .fold(tsubasaErrorMapper::toResponse) { Response.ok(it).build() }

  @POST
  @Path("init")
  fun initialize(): Response =
    iRequestClubs.initialize()
      .fold(tsubasaErrorMapper::toResponse) { Response.ok(it).build() }

}
