package com.shopping.client

import com.shopping.stubs.order.Order
import com.shopping.stubs.order.OrderRequest
import com.shopping.stubs.order.OrderResponse
import com.shopping.stubs.order.OrderServiceGrpc
import io.grpc.Channel
import java.util.logging.Logger


class OrderClient {
    private val logger = Logger.getLogger(
        OrderClient::class.java.name
    )
    private var orderServiceBlockingStub: OrderServiceGrpc.OrderServiceBlockingStub? =
        null

    constructor(channel: Channel?) {
        orderServiceBlockingStub =
            OrderServiceGrpc.newBlockingStub(channel)
    }

    fun getOrders(userId: Int): List<Order?>? {
        logger.info("OrderClient calling the OrderService method")
        val orderRequest =
            OrderRequest.newBuilder().setUserId(userId).build()
        val orderResponse : OrderResponse =
            orderServiceBlockingStub!!.getOrdersForUser(orderRequest)
        return orderResponse.orderList
    }
}