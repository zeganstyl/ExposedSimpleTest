import com.google.common.base.Stopwatch
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.concurrent.TimeUnit

object ExposedTable : IntIdTable("test_table") {
    val key = integer("key").uniqueIndex()
    val value = bool("value")
}

class ExposedEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ExposedEntity>(ExposedTable)
    var key by ExposedTable.key
    var value by ExposedTable.value
}

fun testExposedDsl(database: Database) {
    var sw = Stopwatch.createStarted()
    repeat(10000) { i ->
        transaction(database) {
            ExposedTable.insert {
                it[key] = i
                it[value] = true
            }
            commit()
        }
    }
    println("- exposed dsl > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
    sw = Stopwatch.createStarted()
    repeat(10000) { i ->
        transaction(database) {
            ExposedTable.select {
                ExposedTable.key eq i
            }.firstOrNull()?.get(ExposedTable.value)
        }
    }
    println("- exposed dsl > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
}

fun testExposedTransactionOnly(database: Database) {
    var sw = Stopwatch.createStarted()
    repeat(10000) {
        transaction(database) {
            exec("INSERT INTO test_table (`key`,`value`) VALUES ($it, true)")
        }
    }
    println("- exposed transaction only > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
    sw = Stopwatch.createStarted()
    repeat(10000) {
        transaction(database) {
            exec("SELECT * FROM `test_table` WHERE `key`=$it") {
                if (it.next()) {
                    it.getBoolean("value")
                }
            }
        }
    }
    println("- exposed transaction only > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
}

fun testExposedDao(database: Database) {
    var sw = Stopwatch.createStarted()
    repeat(10000) { i ->
        transaction(database) {
            ExposedEntity.new {
                key = i
                value = true
            }
        }
    }
    println("- exposed dao > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
    sw = Stopwatch.createStarted()
    for (i in 1..10000) {
        transaction(database) {
            ExposedEntity[i].value
        }
    }
    println("- exposed dao > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
}