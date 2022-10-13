package org.sothea.tsubasa.infra.redis.adapter

import arrow.core.*
import arrow.core.Validated.Companion.catch
import io.quarkus.redis.datasource.ReactiveRedisDataSource
import io.smallrye.mutiny.Uni
import org.sothea.tsubasa.domain.error.TechnicalError.GenericTechnicalError
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

  companion object {
    private val CLUBS: Set<Club> = setOf(
      Club(
        name = "FC Barcelona",
        players = setOf(Player(firstName = Some("Tsubasa"), lastName = "Ohzora", nationality = "Japan"))
      ),
      Club(name = "Real Madrid", players = setOf(Player(lastName = "Natureza", nationality = "Brasil")))
    )
  }

  override fun findClubs() =
    CLUBS.let(Uni.createFrom()::item)
      .valid()

  override fun initialize(): ValidatedNel<TsubasaError, Uni<Set<Club>>> =
    CLUBS.traverseValidated { club ->
      catch {
        reactiveRedisDataSource.value(Club::class.java)
          .set(club.name, club)
      }
        .mapLeft { GenericTechnicalError(message = it.localizedMessage) }
        .toValidatedNel()
    }
      .flatMap {
        catch {
          reactiveRedisDataSource.key(Club::class.java)
            .keys("*")
            .map(List<Club>::toSet)
        }
          .mapLeft { GenericTechnicalError(message = it.localizedMessage) }
          .toValidatedNel()
      }

}
