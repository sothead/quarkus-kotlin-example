package org.sothea.tsubasa.domain.error

import org.sothea.tsubasa.domain.error.TsubasaError.ErrorType.TECHNICAL

sealed class TechnicalError(
  override val code: String,
  override val message: String
) : TsubasaError(type = TECHNICAL, code = code, message = message) {

  data class RedisError(val throwable: Throwable) :
    TechnicalError(
      code = "REDIS_ERROR",
      message = "Redis call failed : [${throwable.localizedMessage}]"
    )

  data class GenericTechnicalError(override val message: String) :
    TechnicalError(
      code = "GENERIC_TECHNICAL_ERROR",
      message = message
    )

}
