package com.shopping.db

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger

class OrderDao {
    val logger = Logger.getLogger(
        OrderDao::class.java.name
    )

    fun getOrders(userId: Int): MutableList<Order> {
        var connection: Connection? = null
        val orders: MutableList<Order> = ArrayList()
        try {
            connection =
                H2DatabaseConnection.getConnectionToDatabase()
            var preparedStatement: PreparedStatement? = null
            preparedStatement =
                connection!!.prepareStatement("select * from orders where user_id=?")
            preparedStatement.setInt(1, userId)
            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                val order = Order()
                order.setUserId(resultSet.getInt("user_id"))
                order.setOrderId(resultSet.getInt("order_id"))
                order.setNoOfItems(resultSet.getInt("no_of_items"))
                order.setTotalAmount(resultSet.getDouble("total_amount"))
                order.setOrderDate(resultSet.getDate("order_date"))
                orders.add(order)
            }
        } catch (exception: SQLException) {
            logger.log(
                Level.SEVERE,
                "Could not execute query",
                exception
            )
        }
        return orders
    }
}