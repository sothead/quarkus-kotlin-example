package org.sothea.tsubasa.domain.error

import org.sothea.tsubasa.domain.error.TsubasaError.ErrorType.FUNCTIONAL

sealed class FunctionalError(
  override val code: String,
  override val message: String,
) : TsubasaError(type = FUNCTIONAL, code = code, message = message) {

  data class RequiredFieldError(private val field: String) :
    FunctionalError(
      code = "REQUIRED_FIELD_ERROR",
      message = "Field [$field] required"
    )

}