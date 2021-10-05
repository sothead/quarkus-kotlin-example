package org.sothea.tsubasa.infra.redis.adapter

import arrow.core.Some
import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.redis.client.reactive.ReactiveRedisClient
import io.smallrye.mutiny.Uni
import org.sothea.tsubasa.domain.model.Club
import org.sothea.tsubasa.domain.model.Player
import org.sothea.tsubasa.domain.port.IObtainClubs
import javax.inject.Singleton

@Singleton
class IObtainClubsAdapter(
  private val reactiveRedisClient: ReactiveRedisClient,
  private val objectMapper: ObjectMapper
) : IObtainClubs {

  override fun findClubs() =
    setOf(
      Club(
        name = "FC Barcelona",
        players = setOf(Player(firstName = Some("Tsubasa"), lastName = "Ohzora", nationality = "Japan"))
      ),
      Club(name = "Real Madrid", players = setOf(Player(lastName = "Natureza", nationality = "Brasil")))
    )
      .let(Uni.createFrom()::item)

  override fun initialize() =
    setOf(
      Club(
        name = "FC Barcelona",
        players = setOf(Player(firstName = Some("Tsubasa"), lastName = "Ohzora", nationality = "Japan"))
      ),
      Club(name = "Real Madrid", players = setOf(Player(lastName = "Natureza", nationality = "Brasil")))
    )
      .forEach() {
        reactiveRedisClient.set(listOf(it.name, objectMapper.writeValueAsString(it)))
      }
      .let {
        reactiveRedisClient.keys("*")
          .map { responses ->
            responses.map { objectMapper.readValue(it.toString(), Club::class.java) }
          }
      }
      .map(List<Club>::toSet)


}