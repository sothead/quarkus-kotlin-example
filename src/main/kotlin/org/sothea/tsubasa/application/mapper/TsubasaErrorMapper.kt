package org.sothea.tsubasa.application.mapper

import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf
import org.jboss.resteasy.reactive.RestResponse.StatusCode.*
import org.sothea.tsubasa.domain.error.FunctionalError.RequiredFieldError
import org.sothea.tsubasa.domain.error.TechnicalError.GenericTechnicalError
import org.sothea.tsubasa.domain.error.TechnicalError.RedisError
import org.sothea.tsubasa.domain.error.TsubasaError
import javax.inject.Singleton
import javax.ws.rs.core.Response

internal fun TsubasaError.toHttpStatus(): Int =
  when (this) {
    is RequiredFieldError -> BAD_REQUEST
    is RedisError -> BAD_GATEWAY
    is GenericTechnicalError -> INTERNAL_SERVER_ERROR
  }

@Singleton
class TsubasaErrorMapper {

  fun toResponse(tsubasaError: TsubasaError): Response =
    toResponse(nonEmptyListOf(tsubasaError))

  fun toResponse(tsubasaErrors: NonEmptyList<TsubasaError>): Response =
    tsubasaErrors.map(TsubasaError::toHttpStatus)
      .max()
      .let { Response.status(it).entity(tsubasaErrors).build() }

}
