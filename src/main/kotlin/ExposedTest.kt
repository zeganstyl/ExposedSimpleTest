import com.google.common.base.Stopwatch
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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
    Class.forName("com.mysql.cj.jdbc.Driver");
    val url =
        "jdbc:mysql://10.0.0.21:3306/test?user=root&password=password&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false"
    val database = Database.connect(url, user = "root", password = "password")
    // warm
    resetTable(database)
    testNative(DriverManager.getConnection(url), log = false) //insert time: 11086ms ,select time: 4312ms

    resetTable(database)
    testNative(DriverManager.getConnection(url)) //insert time: 11086ms ,select time: 4312ms

    resetTable(database)
    testNative2(DriverManager.getConnection(url))// insert time: 9324ms  select time: 2529ms

    resetTable(database)
    testExposed(database) // insert time: 37041ms ,select time: 27878ms

    resetTable(database)
    testExposed2(database) // insert time: 37041ms ,select time: 27878ms
}

fun resetTable(database: Database) {
    transaction(database) {
        SchemaUtils.drop(TestLongTable)
        SchemaUtils.createMissingTablesAndColumns(TestLongTable)
    }
}

fun testExposed(database: Database) {
    var sw = Stopwatch.createStarted()
    transaction(database) {
        repeat(10000) { i ->
            TestLongTable.insert {
                it[key] = i
                it[value] = true
            }
            commit()
        }
    }
    println("exposed > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
    sw = Stopwatch.createStarted()
    transaction(database) {
        repeat(10000) { i ->
            TestLongTable.select {
                TestLongTable.key eq i
            }.firstOrNull()?.get(TestLongTable.value)
        }
    }
    println("exposed > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")

}

fun testNative(connection: Connection, log: Boolean = true) {
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
        println("native > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
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
        println("native > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")

}

fun testExposed2(database: Database) {
    var sw = Stopwatch.createStarted()
    transaction(database) {
        repeat(10000) {
            exec("INSERT INTO test_table (`key`,`value`) VALUES ($it, true)")
        }
    }
    println("exposed2 > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
    sw = Stopwatch.createStarted()
    transaction(database) {
        repeat(10000) {
            exec("SELECT * FROM `test_table` WHERE `key`=$it") {
                if (it.next()) {
                    it.getBoolean("value")
                }
            }

        }
    }
    println("exposed2 > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")

}

fun testNative2(connection: Connection) {
    connection.autoCommit = true
    var sw = Stopwatch.createStarted()
    repeat(10000) {
        connection.createStatement().execute("INSERT INTO test_table (`key`,`value`) VALUES ($it, true)")
    }
    println("native2 > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
    sw = Stopwatch.createStarted()
    repeat(10000) {
        connection.createStatement().executeQuery("SELECT * FROM `test_table` WHERE `key`=$it").apply {
            if (next()) {
                getBoolean("value")
            }
        }

    }
    println("native2 > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")

}

object TestLongTable : LongIdTable("test_table") {
    val key = integer("key").uniqueIndex()
    val value = bool("value")
}


class ExposedTest {


}
