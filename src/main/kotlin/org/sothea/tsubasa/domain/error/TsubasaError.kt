package org.sothea.tsubasa.domain.error

sealed class TsubasaError(
  private val type: ErrorType,
  open val code: String,
  open val message: String,
) {

  companion object {
    private const val PROJECT_CODE = "TSUBASA"
    private const val KO_STATUS = "KO"

    const val DEFAULT_ERROR_CODE = "T.$PROJECT_CODE.$KO_STATUS.DEFAULT_ERROR"
  }

  enum class ErrorType(val code: String) {
    FUNCTIONAL("F"), TECHNICAL("T")
  }

  fun computeErrorCode(): String =
    "${type.code}.$PROJECT_CODE.$KO_STATUS.$code"

}
