package org.sothea.tsubasa.domain.error

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.sothea.tsubasa.domain.error.TechnicalError.GenericTechnicalError
import org.sothea.tsubasa.domain.error.TechnicalError.RedisError

internal class TechnicalErrorTest {

  private companion object {

    @JvmStatic
    fun buildTechnicalErrors(): List<Arguments> =
      mapOf(
        RedisError(RuntimeException("Redis error"))
            to ("Redis call failed : [Redis error]" to "T.TSUBASA.KO.REDIS_ERROR"),
        GenericTechnicalError("generic fail")
            to ("generic fail" to "T.TSUBASA.KO.GENERIC_TECHNICAL_ERROR")
      )
        .map { (technicalError, expected) -> arguments(technicalError, expected) }

  }

  @ParameterizedTest
  @MethodSource("buildTechnicalErrors")
  internal fun `should return right message and right code`(
    technicalError: TechnicalError,
    expected: Pair<String, String>
  ) {
    // GIVEN

    // WHEN
    val messageResult = technicalError.message
    val computedCodeResult = technicalError.computeErrorCode()

    // THEN
    expected.also { (expectedMessage, expectedComputedCode) ->
      assertAll(
        { messageResult `should be equal to` expectedMessage },
        { computedCodeResult `should be equal to` expectedComputedCode }
      )
    }
  }

}
