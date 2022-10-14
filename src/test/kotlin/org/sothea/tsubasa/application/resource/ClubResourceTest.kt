package org.sothea.tsubasa.application.resource

import arrow.core.invalid
import arrow.core.valid
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.smallrye.mutiny.Uni
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.sothea.tsubasa.application.mapper.TsubasaErrorMapper
import org.sothea.tsubasa.domain.error.TsubasaError
import org.sothea.tsubasa.domain.model.Club
import org.sothea.tsubasa.domain.port.request.IRequestClubs
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.OK

@ExtendWith(MockKExtension::class)
internal class ClubResourceTest {

  @MockK
  private lateinit var iRequestClubs: IRequestClubs

  @MockK
  private lateinit var tsubasaErrorMapper: TsubasaErrorMapper

  @InjectMockKs
  private lateinit var clubResource: ClubResource

  @Test
  internal fun `should return Response OK with clubs`() {
    // GIVEN
    val clubs = mockk<Uni<Set<Club>>>()
      .apply { every { iRequestClubs.findClubs() } returns this.valid() }

    // WHEN
    val result = clubResource.findClubs()

    // THEN
    assertAll(
      { result.status `should be equal to` OK.statusCode },
      { result.entity `should be equal to` clubs }
    )

    verify { iRequestClubs.findClubs() }
  }

  @Test
  internal fun `should return error Response when invalid clubs finding`() {
    // GIVEN
    val errorResponse = mockk<Response>()
    val tsubasaError = mockk<TsubasaError>()
      .also {
        every { iRequestClubs.findClubs() } returns it.invalid()
        every { tsubasaErrorMapper.toResponse(it) } returns errorResponse
      }


    // WHEN
    val result = clubResource.findClubs()

    // THEN
    result `should be equal to` errorResponse

    verifyOrder {
      iRequestClubs.findClubs()
      tsubasaErrorMapper.toResponse(tsubasaError)
    }
  }
}
