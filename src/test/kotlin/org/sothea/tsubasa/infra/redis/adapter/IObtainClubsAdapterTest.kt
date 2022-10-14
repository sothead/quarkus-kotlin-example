package org.sothea.tsubasa.infra.redis.adapter

import arrow.core.invalid
import arrow.core.valid
import arrow.core.valueOr
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.quarkus.redis.datasource.ReactiveRedisDataSource
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands
import io.quarkus.redis.datasource.value.ReactiveValueCommands
import io.smallrye.mutiny.Uni
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.sothea.tsubasa.domain.error.TechnicalError.RedisError
import org.sothea.tsubasa.domain.model.Club
import org.sothea.tsubasa.infra.redis.adapter.IObtainClubsAdapter.Companion.CLUBS
import java.time.Duration.ofSeconds

@ExtendWith(MockKExtension::class)
internal class IObtainClubsAdapterTest {

  @MockK
  private lateinit var reactiveRedisDataSource: ReactiveRedisDataSource

  @SpyK
  @InjectMockKs
  private lateinit var iObtainClubsAdapter: IObtainClubsAdapter

  @Test
  internal fun `should return valid clubs`() {
    // GIVEN
    val club = mockk<Club>()
    val reactiveKeyCommands = mockk<ReactiveKeyCommands<Club>>()
      .also {
        every { it.keys("*") } returns Uni.createFrom().item(mutableListOf(club))
        every { reactiveRedisDataSource.key(Club::class.java) } returns it
      }

    // WHEN
    val result = iObtainClubsAdapter.findClubs()

    // THEN
    result.valueOr { fail("should not be invalid!") }
      .await()
      .atMost(ofSeconds(1)) `should be equal to` setOf(club)

    verifyOrder {
      reactiveRedisDataSource.key(Club::class.java)
      reactiveKeyCommands.keys("*")
    }
  }

  @Test
  internal fun `should return valid clubs when initialize`() {
    // GIVEN
    val reactiveValueCommands = mockk<ReactiveValueCommands<String, Club>>()
      .also {
        every { it.mset(CLUBS.associateBy(Club::name)) } returns mockk<Uni<Void>>()
        every { reactiveRedisDataSource.value(Club::class.java) } returns it
      }

    every { iObtainClubsAdapter.findClubs() } returns Uni.createFrom().item(CLUBS).valid()

    // WHEN
    val result = iObtainClubsAdapter.initialize()

    // THEN
    result.valueOr { fail("should not be invalid : $it") }
      .await()
      .atMost(ofSeconds(1)) `should be equal to` CLUBS

    verifyOrder {
      reactiveRedisDataSource.value(Club::class.java)
      reactiveValueCommands.mset(CLUBS.associateBy(Club::name))
      iObtainClubsAdapter.findClubs()
    }
  }

  @Test
  internal fun `should return invalid when Redis call failed`() {
    // GIVEN
    val throwable = RuntimeException("Redis call failed")
      .apply { every { reactiveRedisDataSource.value(Club::class.java) } throws this }


    // WHEN
    val result = iObtainClubsAdapter.initialize()

    // THEN
    result `should be equal to` RedisError(throwable).invalid()

    verify { reactiveRedisDataSource.value(Club::class.java) }
  }
}
