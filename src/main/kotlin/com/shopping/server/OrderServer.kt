package com.shopping.server

import com.shopping.service.OrderServiceImpl
import io.grpc.Server
import io.grpc.ServerBuilder
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

class OrderServer {
    private val logger = Logger.getLogger(
        OrderServer::class.java.name
    )
    private var server: Server? = null

    fun startServer() {
        val port = 50052
        try {
            server = ServerBuilder.forPort(port)
                .addService(OrderServiceImpl())
                .build()
                .start()
            logger.info("Order Server started on port 50052")
            Runtime.getRuntime().addShutdownHook(object : Thread() {
                override fun run() {
                    logger.info("Clean server shutdown in case JVM was shutdown")
                    try {
                        stopServer()
                    } catch (exception: InterruptedException) {
                        logger.log(
                            Level.SEVERE,
                            "Server shutdown interrupted",
                            exception
                        )
                    }
                }
            })
        } catch (exception: IOException) {
            logger.log(
                Level.SEVERE,
                "Server did not start",
                exception
            )
        }
    }

    @Throws(InterruptedException::class)
    fun stopServer() {
        if (server != null) {
            server!!.shutdown().awaitTermination(30, TimeUnit.SECONDS)
        }
    }

    @Throws(InterruptedException::class)
    fun blockUntilShutdown() {
        if (server != null) {
            server!!.awaitTermination()
        }
    }

}

fun main(args:Array<String>) {
    val orderServer = OrderServer()
    orderServer.startServer()
    orderServer.blockUntilShutdown()

}