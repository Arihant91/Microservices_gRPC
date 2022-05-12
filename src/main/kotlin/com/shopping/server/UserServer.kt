package com.shopping.server

import com.shopping.service.OrderServiceImpl
import com.shopping.service.UserServiceImpl
import io.grpc.Server
import io.grpc.ServerBuilder
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

internal class UserServer {
    private var server: Server? = null
    fun startServer() {
        val port = 50051
        try {
            server = ServerBuilder.forPort(port).addService(
                OrderServiceImpl()
            ).addService(UserServiceImpl())
                .build()
                .start()
            logger.info("Server started on port 50051")
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

    companion object {
        private val logger = Logger.getLogger(
            UserServer::class.java.name
        )

    }
}


fun main(args: Array<String>) {
    val userServer = UserServer()
    userServer.startServer()
    userServer.blockUntilShutdown()
}