package com.shopping.service

import com.shopping.stubs.order.Order
import com.shopping.stubs.user.Gender
import com.shopping.stubs.user.UserRequest
import com.shopping.stubs.user.UserResponse
import com.shopping.stubs.user.UserServiceGrpc
import com.shopping.client.OrderClient
import com.shopping.db.UserDao

import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

class UserServiceImpl : UserServiceGrpc.UserServiceImplBase() {
    private val logger = Logger.getLogger(
        UserServiceImpl::class.java.name
    )
    private val userDao = UserDao()
    override fun getUserDetails(
        request: UserRequest,
        responseObserver: StreamObserver<UserResponse?>
    ) {
        val user = userDao.getDetails(request.username)
        val userResponseBuilder = UserResponse.newBuilder()
            .setId(user!!.getId())
            .setUsername(user!!.getUsername())
            .setName(user!!.getName())
            .setAge(user!!.getAge())
            .setGender(Gender.valueOf(user.getGender()!!))
        val orders = getOrders(userResponseBuilder)
        userResponseBuilder.noOfOrders = orders!!.size
        val userResponse = userResponseBuilder.build()
        responseObserver.onNext(userResponse)
        responseObserver.onCompleted()
    }

    private fun getOrders(userResponseBuilder: UserResponse.Builder): List<Order?>? {
        //get orders by invoking the Order Client
        logger.info("Creating a channel and calling the Order Client")
        val channel =
            ManagedChannelBuilder.forTarget("localhost:50052")
                .usePlaintext().build()
        val orderClient = OrderClient(channel)
        val orders = orderClient.getOrders(userResponseBuilder.id)
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
        } catch (exception: InterruptedException) {
            logger.log(
                Level.SEVERE,
                "Channel did not shutdown",
                exception
            )
        }
        return orders
    }
}