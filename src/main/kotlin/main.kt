import com.google.common.base.Stopwatch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.TimeUnit

/**
 * @author  MeowRay
 * @date  2021/8/9 20:30
 * @version 1.0
 */


fun main() {
    val url = "jdbc:h2:file:./db-test"
    val database = Database.connect(url)
    val connection = DriverManager.getConnection(url)

    // warm
    resetTable(database)
    testPlainJdbcAutoCommitFalse(DriverManager.getConnection(url), log = false)

    resetTable(database)
    testPlainJdbcAutoCommitFalse(DriverManager.getConnection(url))

    resetTable(database)
    testPlainJdbcAutoCommitTrue(DriverManager.getConnection(url))

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
        println("testNativeAutoCommitFalse > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
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
        println("testNativeAutoCommitFalse > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")

}

fun testPlainJdbcAutoCommitTrue(connection: Connection) {
    connection.autoCommit = true
    var sw = Stopwatch.createStarted()
    repeat(10000) {
        connection.createStatement().execute("INSERT INTO test_table (`key`,`value`) VALUES ($it, true)")
    }
    println("testNativeAutoCommitTrue > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
    sw = Stopwatch.createStarted()
    repeat(10000) {
        connection.createStatement().executeQuery("SELECT * FROM `test_table` WHERE `key`=$it").apply {
            if (next()) {
                getBoolean("value")
            }
        }
    }
    println("testNativeAutoCommitTrue > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
}