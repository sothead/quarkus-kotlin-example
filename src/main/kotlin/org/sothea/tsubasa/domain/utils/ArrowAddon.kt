package org.sothea.tsubasa.domain.utils

import arrow.core.Validated
import arrow.core.Validated.Invalid
import arrow.core.identity

fun <E, A, B> Validated<E, A>.flatMap(action: (A) -> Validated<E, B>): Validated<E, B> =
  fold(::Invalid, action)

fun <E, A> Validated<E, Validated<E, A>>.flatten(): Validated<E, A> =
  fold(::Invalid, ::identity)
