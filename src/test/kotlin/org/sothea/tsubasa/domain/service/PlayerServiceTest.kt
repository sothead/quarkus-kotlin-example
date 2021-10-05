package org.sothea.tsubasa.domain.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.smallrye.mutiny.Uni
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.sothea.tsubasa.domain.model.Player
import org.sothea.tsubasa.domain.port.IObtainPlayers

@ExtendWith(MockKExtension::class)
internal class PlayerServiceTest {

  @MockK
  private lateinit var iObtainPlayers: IObtainPlayers

  @InjectMockKs
  private lateinit var playerService: PlayerService

  @Test
  internal fun `should return players`() {
    // GIVEN
    val players = mockk<Uni<Set<Player>>>()
    every { iObtainPlayers.findPlayers() } returns players

    // WHEN
    val result = playerService.findPlayers()

    // THEN
    result `should be equal to` players
  }

}