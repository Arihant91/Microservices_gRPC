package com.shopping.db

import java.util.*

class Order {
    private var userId = 0
    private var orderId = 0
    private var noOfItems = 0
    private var totalAmount = 0.0
    private var orderDate: Date? = null

    fun getUserId(): Int {
        return userId
    }

    fun setUserId(userId: Int) {
        this.userId = userId
    }

    fun getOrderId(): Int {
        return orderId
    }

    fun setOrderId(orderId: Int) {
        this.orderId = orderId
    }

    fun getNoOfItems(): Int {
        return noOfItems
    }

    fun setNoOfItems(noOfItems: Int) {
        this.noOfItems = noOfItems
    }

    fun getTotalAmount(): Double {
        return totalAmount
    }

    fun setTotalAmount(totalAmount: Double) {
        this.totalAmount = totalAmount
    }

    fun getOrderDate(): Date? {
        return orderDate
    }

    fun setOrderDate(orderDate: Date?) {
        this.orderDate = orderDate
    }
}