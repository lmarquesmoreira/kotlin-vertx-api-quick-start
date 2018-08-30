package br.com.lucas.vertx

import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router


fun main(args: Array<String>) {

    println("Hello, world")

    val vertx = Vertx.vertx()
    val httpServer = vertx.createHttpServer()

    val router = Router.router(vertx)

    router.get("/")
            .handler { routingContext ->
                val response = routingContext.response()
                response
                        .putHeader("Content-Type", "text/plain")
                        .setChunked(true)
                        .write("Hi\n")
                        .end("Ended")
            }

    router.get("/json/:name")
            .handler {
                val name = it.request().getParam("name")
                it.response()
                        .putHeader("Content-Type", "application/json")
                        .setChunked(true)
                        .write(Json.encodePrettily(ResponseObj("Hello, $name")))
                        .end()
            }

    httpServer
            .requestHandler(router::accept)
            .listen(8091)
}


data class ResponseObj(val name: String = "")