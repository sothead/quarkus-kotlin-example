package org.sothea.tsubasa.domain.service

import arrow.core.valid
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
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
  internal fun `should return clubs`() {
    // GIVEN
    val clubs = mockk<Uni<Set<Club>>>().valid()
    every { iObtainClubs.findClubs() } returns clubs

    // WHEN
    val result = clubService.findClubs()

    // THEN
    result `should be equal to` clubs
  }

}
