package org.sothea.tsubasa.application.mapper

import arrow.core.nonEmptyListOf
import io.mockk.mockk
import org.amshove.kluent.`should be equal to`
import org.jboss.resteasy.reactive.RestResponse.StatusCode.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.sothea.tsubasa.domain.error.FunctionalError.RequiredFieldError
import org.sothea.tsubasa.domain.error.TechnicalError.GenericTechnicalError
import org.sothea.tsubasa.domain.error.TechnicalError.RedisError
import org.sothea.tsubasa.domain.error.TsubasaError


internal class TsubasaErrorMapperKtTest {

  private val tsubasaErrorMapper: TsubasaErrorMapper = TsubasaErrorMapper()

  private companion object {

    @JvmStatic
    fun buildTsubasaErrors(): List<Arguments> =
      mapOf(
        mockk<RequiredFieldError>() to BAD_REQUEST,
        mockk<RedisError>() to BAD_GATEWAY,
        mockk<GenericTechnicalError>() to INTERNAL_SERVER_ERROR
      )
        .map { (tsubasaError, expected) -> arguments(tsubasaError, expected) }

  }

  @ParameterizedTest
  @MethodSource("buildTsubasaErrors")
  internal fun `should return right error Response`(tsubasaError: TsubasaError, expected: Int) {
    // GIVEN
    val tsubasaErrors = nonEmptyListOf(tsubasaError)

    // WHEN
    val result = tsubasaErrorMapper.toResponse(tsubasaErrors)

    // THEN
    assertAll(
      { result.status `should be equal to` expected },
      { result.entity `should be equal to` tsubasaErrors }
    )
  }

  @Test
  internal fun `should return error Response with higher status code when multiple errors`() {
    // GIVEN
    val tsubasaErrors = nonEmptyListOf(mockk<RedisError>(), mockk<RequiredFieldError>())

    // WHEN
    val result = tsubasaErrorMapper.toResponse(tsubasaErrors)

    // THEN
    assertAll(
      { result.status `should be equal to` BAD_GATEWAY },
      { result.entity `should be equal to` tsubasaErrors }
    )
  }

}
