package com.chairboard.database

import com.chairboard.database.tables.*
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object Database {
    private const val KEY_DB_URL = "db.config.db_url"
    private const val KEY_DB_USER = "db.config.db_user"
    private const val KEY_DB_PWD = "db.config.db_pwd"

    private lateinit var instance: Database

    fun init(application: Application) {
        val url = application.environment.config.propertyOrNull(KEY_DB_URL)?.getString() ?: ""
        val user = application.environment.config.propertyOrNull(KEY_DB_USER)?.getString() ?: ""
        val pass = application.environment.config.propertyOrNull(KEY_DB_PWD)?.getString() ?: ""
        val driverClassName = "com.mysql.cj.jdbc.Driver"
        instance = Database.connect(url = url, driver = driverClassName, user = user, password = pass)
        transaction(instance) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UsersTable, AuthUsersTable, TransactionTable, FastagTable, CustomersTable, DocumentTable)
            SchemaUtils.createMissingTablesAndColumns(TransactionTable)
        }
    }
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }