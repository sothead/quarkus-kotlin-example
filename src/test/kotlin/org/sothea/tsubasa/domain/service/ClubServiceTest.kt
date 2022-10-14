package org.sothea.tsubasa.domain.service

import arrow.core.valid
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.smallrye.mutiny.Uni
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.sothea.tsubasa.domain.model.Club
import org.sothea.tsubasa.domain.port.obtain.IObtainClubs

@ExtendWith(MockKExtension::class)
internal class ClubServiceTest {

  @MockK
  private lateinit var iObtainClubs: IObtainClubs

  @InjectMockKs
  private lateinit var clubService: ClubService

  @Test
  internal fun `should return valid clubs`() {
    // GIVEN
    val validClubs = mockk<Uni<Set<Club>>>().valid()
      .also { every { iObtainClubs.findClubs() } returns it }

    // WHEN
    val result = clubService.findClubs()

    // THEN
    result `should be equal to` validClubs

    verify { iObtainClubs.findClubs() }
  }

  @Test
  internal fun `should return valid clubs when initialize`() {
    // GIVEN
    val validClubs = mockk<Uni<Set<Club>>>().valid()
      .also { every { iObtainClubs.initialize() } returns it }

    // WHEN
    val result = clubService.initialize()

    // THEN
    result `should be equal to` validClubs

    verify { iObtainClubs.initialize() }
  }

}
