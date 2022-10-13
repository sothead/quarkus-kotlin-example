package org.sothea.tsubasa.application.resource

import org.sothea.tsubasa.domain.port.request.IRequestClubs
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("tsubasa/clubs")
class ClubResource(
  private val iRequestClubs: IRequestClubs
) {

  @GET
  fun findClubs(): Response =
    iRequestClubs.findClubs()
      .fold(
        { Response.serverError().entity(it).build() },
        { Response.ok(it).build() }
      )

  @POST
  @Path("init")
  fun initialize(): Response =
    iRequestClubs.initialize()
      .fold(
        { Response.serverError().entity(it).build() },
        { Response.ok(it).build() }
      )
}
