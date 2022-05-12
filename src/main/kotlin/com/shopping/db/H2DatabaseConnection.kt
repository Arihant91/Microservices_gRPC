package com.shopping.db

import org.h2.tools.RunScript
import org.h2.tools.Server
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger


class H2DatabaseConnection {



    companion object {

        private val logger: Logger = Logger.getLogger(
            H2DatabaseConnection::class.qualifiedName)
        private var server : Server? = null

        init{
            try{
                initializeDatabase(getConnectionToDatabase())
            } catch (exception : FileNotFoundException) {
                logger.log(
                    Level.SEVERE,
                    "Could not find the .sql file",
                    exception
                )
            } catch (exception : SQLException) {
                logger.log(Level.SEVERE, "SQL error", exception)
            }
        }

        fun getConnectionToDatabase() : Connection {
            var connection: Connection? = null
            try {
                Class.forName("org.h2.Driver")
                connection = DriverManager.getConnection(
                    "jdbc:h2:mem:shoppingDb", "", ""
                )
            } catch (exception: Exception) {
                logger.log(
                    Level.SEVERE,
                    "Could not set up connection",
                    exception
                )
            }
            logger.info("Connection set up completed")
            return connection!!
        }

        @Throws(SQLException::class)
        fun startDatabase() {
            server =
                Server.createTcpServer().start()
        }

        fun stopDatabase() {
            server?.stop()
        }

        fun initializeDatabase(conn: Connection) {
            val resource =
                H2DatabaseConnection::class.java.classLoader.getResourceAsStream(
                    "initialize.sql"
                )
            RunScript.execute(conn, InputStreamReader(resource))
        }

    }


}