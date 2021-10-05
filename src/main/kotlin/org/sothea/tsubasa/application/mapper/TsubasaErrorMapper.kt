//package org.sothea.tsubasa.application.mapper
//
//import org.jboss.resteasy.specimpl.BuiltResponse
//import org.sothea.tsubasa.domain.error.FunctionalError
//import org.sothea.tsubasa.domain.error.FunctionalError.RequiredFieldError
//import org.sothea.tsubasa.domain.error.TsubasaError
//import javax.ws.rs.BadRequestException
//import javax.ws.rs.core.Response
//
//fun TsubasaError.toHttpStatusException() =
//
//  when (this) {
//    is RequiredFieldError -> BuiltResponse.IN(Response)
//    else -> INTERNAL_SERVER_ERROR
//  }