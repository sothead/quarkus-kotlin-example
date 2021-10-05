package org.sothea.tsubasa.infra.redis.adapter

import arrow.core.Some
import io.quarkus.redis.client.reactive.ReactiveRedisClient
import io.smallrye.mutiny.Uni
import org.sothea.tsubasa.domain.model.Player
import org.sothea.tsubasa.domain.port.IObtainPlayers
import javax.inject.Singleton

@Singleton
class IObtainPlayersAdapter(reactiveRedisClient: ReactiveRedisClient) : IObtainPlayers {

  override fun findPlayers() =
    setOf(
      Player(firstName = Some("Tsubasa"), lastName = "Ohzora", nationality = "Japan"),
      Player(lastName = "Natureza", nationality = "Brasil")
    )
      .let(Uni.createFrom()::item)
}