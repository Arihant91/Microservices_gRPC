package com.shopping.service;

import com.google.protobuf.util.Timestamps
import com.shopping.stubs.order.OrderRequest
import com.shopping.stubs.order.OrderResponse
import com.shopping.stubs.order.OrderServiceGrpc
import io.grpc.stub.StreamObserver
import com.shopping.db.Order
import com.shopping.db.OrderDao
import java.util.logging.Logger
import java.util.stream.Collectors


class OrderServiceImpl : OrderServiceGrpc.OrderServiceImplBase() {
    val logger = Logger.getLogger(
        OrderServiceImpl::class.qualifiedName
    )
    val orderDao = OrderDao()


    override fun getOrdersForUser(
        request: OrderRequest,
        responseObserver: StreamObserver<OrderResponse?>
    ) {
        val orders: MutableList<Order> = orderDao.getOrders(request.userId)
        logger.info("Got orders from OrderDao and converting to OrderResponse proto objects")
        val ordersForUser = orders.stream().map { order: Order ->
            com.shopping.stubs.order.Order.newBuilder()
                .setUserId(order.getUserId())
                .setOrderId(order.getOrderId())
                .setNoOfItems(order.getNoOfItems())
                .setTotalAmount(order.getTotalAmount())
                .setOrderDate(
                    Timestamps.fromMillis(
                        order.getOrderDate()!!.time
                    )
                ).build()
        }
            .collect(Collectors.toList())
        val orderResponse =
            OrderResponse.newBuilder().addAllOrder(ordersForUser)
                .build()
        responseObserver.onNext(orderResponse)
        responseObserver.onCompleted()

    }

}