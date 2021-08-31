import com.google.common.base.Stopwatch
import org.h2.jdbcx.JdbcConnectionPool
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.TimeUnit
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

/**
 * @author  MeowRay, zeganstyl
 * @date  2021/8/9 20:30
 * @version 1.0
 */
fun main() {
    val dbUrl = "jdbc:h2:file:./db-test"
    val useHikari = false

    val database = if (useHikari) {
        val config = HikariConfig().apply {
            driverClassName = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:file:./db-test"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        Database.connect(HikariDataSource(config))
    } else {
        val pool = JdbcConnectionPool.create(dbUrl, "", "")
        Database.connect(pool)
    }

    // warm
    resetTable(database)
    testPlainJdbcAutoCommitFalse(DriverManager.getConnection(dbUrl), log = false)

    resetTable(database)
    testPlainJdbcAutoCommitFalse(DriverManager.getConnection(dbUrl))

    resetTable(database)
    testPlainJdbcAutoCommitTrue(DriverManager.getConnection(dbUrl))

    resetTable(database)
    testExposedDsl(database)

    resetTable(database)
    testExposedTransactionOnly(database)

    resetTable(database)
    testExposedDao(database)

    resetTable(database)
    Hibernate.testDao()
}

fun resetTable(database: Database) {
    transaction(database) {
        SchemaUtils.drop(ExposedTable)
        SchemaUtils.createMissingTablesAndColumns(ExposedTable)
    }
}

fun testPlainJdbcAutoCommitFalse(connection: Connection, log: Boolean = true) {
    connection.autoCommit = false
    var sw = Stopwatch.createStarted()
    repeat(10000) {
        val prepareStatement = connection.prepareStatement("INSERT INTO test_table (`key`,`value`) VALUES (?, ?)")
        prepareStatement.apply {
            setInt(1, it)
            setBoolean(2, true)
        }.executeUpdate()
        prepareStatement.close()
        connection.commit()
    }
    if (log)
        println("- plain jdbc autoCommit=false > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
    sw = Stopwatch.createStarted()
    repeat(10000) {
        val prepareStatement = connection.prepareStatement("SELECT * FROM `test_table` WHERE `key`=?")
        prepareStatement.setInt(1, it)
        prepareStatement.executeQuery().apply {
            if (next()) {
                getBoolean("value")
            }
            close()
        }
        prepareStatement.close()
        connection.commit()
    }
    if (log)
        println("- plain jdbc autoCommit=false > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")

}

fun testPlainJdbcAutoCommitTrue(connection: Connection) {
    connection.autoCommit = true
    var sw = Stopwatch.createStarted()
    repeat(10000) {
        connection.createStatement().execute("INSERT INTO test_table (`key`,`value`) VALUES ($it, true)")
    }
    println("- plain jdbc autoCommit=true > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
    sw = Stopwatch.createStarted()
    repeat(10000) {
        connection.createStatement().executeQuery("SELECT * FROM `test_table` WHERE `key`=$it").apply {
            if (next()) {
                getBoolean("value")
            }
        }
    }
    println("- plain jdbc autoCommit=true > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
}