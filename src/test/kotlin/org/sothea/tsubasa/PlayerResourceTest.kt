package org.sothea.tsubasa

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@QuarkusTest
class PlayerResourceTest {

  @Disabled
  @Test
  fun `first test`() {
    given()
      .`when`().get("/tsubasa/characters")
      .then()
      .statusCode(200)
      .body(`is`("Hello RESTEasy"))
  }

}