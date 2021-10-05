package org.sothea.tsubasa.application.configuration

import arrow.integrations.jackson.module.registerArrowModule
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.quarkus.jackson.ObjectMapperCustomizer
import javax.inject.Singleton

@Singleton
class RegisterCustomModuleCustomizer : ObjectMapperCustomizer {

  override fun customize(objectMapper: ObjectMapper) {
    objectMapper.apply {
      enable(WRAP_ROOT_VALUE)
        .setSerializationInclusion(NON_EMPTY)
        .registerModule(KotlinModule())
        .registerArrowModule()
    }
  }

}