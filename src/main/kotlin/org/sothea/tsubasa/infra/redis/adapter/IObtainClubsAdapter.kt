package org.sothea.tsubasa.infra.redis.adapter

import arrow.core.*
import arrow.core.Validated.Companion.catch
import io.quarkus.redis.datasource.ReactiveRedisDataSource
import io.smallrye.mutiny.Uni
import org.sothea.tsubasa.domain.error.TechnicalError.RedisError
import org.sothea.tsubasa.domain.error.TsubasaError
import org.sothea.tsubasa.domain.model.Club
import org.sothea.tsubasa.domain.model.Player
import org.sothea.tsubasa.domain.port.obtain.IObtainClubs
import org.sothea.tsubasa.domain.utils.flatMap
import javax.inject.Singleton

@Singleton
class IObtainClubsAdapter(
  private val reactiveRedisDataSource: ReactiveRedisDataSource
) : IObtainClubs {

  internal companion object {
    internal val CLUBS: Set<Club> = setOf(
      Club(
        name = "FC Barcelona",
        players = setOf(Player(firstName = Some("Tsubasa"), lastName = "Ohzora", nationality = "Japan"))
      ),
      Club(
        name = "Real Madrid",
        players = setOf(Player(lastName = "Natureza", nationality = "Brasil"))
      )
    )
  }

  override fun findClubs() =
    catch {
      reactiveRedisDataSource.key(Club::class.java)
        .keys("*")
        .map(List<Club>::toSet)
    }
      .mapLeft(::RedisError)

  override fun initialize(): Validated<TsubasaError, Uni<Set<Club>>> =
    CLUBS.associateBy(Club::name)
      .let {
        catch {
          reactiveRedisDataSource.value(Club::class.java)
            .mset(it)
        }
          .mapLeft(::RedisError)
      }
      .flatMap { findClubs() }

}
