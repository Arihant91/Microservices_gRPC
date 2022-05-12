package com.shopping.db

import java.sql.Connection
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger

class UserDao {
    private val logger = Logger.getLogger(
        UserDao::class.java.name
    )

    fun getDetails(username: String?): User? {
        val user = User()
        try {
            val connection: Connection =
                H2DatabaseConnection.getConnectionToDatabase()
            val preparedStatement = connection
                .prepareStatement("select * from user where username=?")
            preparedStatement.setString(1, username)
            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"))
                user.setUsername(resultSet.getString("username"))
                user.setName(resultSet.getString("name"))
                user.setAge(resultSet.getInt("age"))
                user.setGender(resultSet.getString("gender"))
            }
        } catch (exception: SQLException) {
            logger.log(
                Level.SEVERE,
                "Could not execute query",
                exception
            )
        }
        return user
    }
}