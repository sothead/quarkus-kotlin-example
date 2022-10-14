package org.sothea.tsubasa.domain.error

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.sothea.tsubasa.domain.error.FunctionalError.RequiredFieldError

internal class FunctionalErrorTest {

  private companion object {

    @JvmStatic
    fun buildFunctionalErrors(): List<Arguments> =
      mapOf(
        RequiredFieldError("field")
            to ("Field [field] required" to "F.TSUBASA.KO.REQUIRED_FIELD_ERROR")
      )
        .map { (functionalError, expected) -> arguments(functionalError, expected) }

  }

  @ParameterizedTest
  @MethodSource("buildFunctionalErrors")
  internal fun `should return right message and right code`(
    functionalError: FunctionalError,
    expected: Pair<String, String>
  ) {
    // GIVEN

    // WHEN
    val messageResult = functionalError.message
    val computedCodeResult = functionalError.computeErrorCode()

    // THEN
    expected.also { (expectedMessage, expectedComputedCode) ->
      assertAll(
        { messageResult `should be equal to` expectedMessage },
        { computedCodeResult `should be equal to` expectedComputedCode }
      )
    }
  }

}
