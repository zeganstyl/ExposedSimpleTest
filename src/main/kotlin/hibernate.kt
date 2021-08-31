import com.google.common.base.Stopwatch
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

object Hibernate {
    init {
        java.util.logging.Logger.getLogger("org.hibernate").level = Level.OFF
    }

    fun testDao() {
        var sw = Stopwatch.createStarted()
        repeat(10000) {
            sessionFactory.openSession().apply {
                transaction.begin()
                persist(HibernateEntity().apply {
                    key = it
                    value = true
                })
                transaction.commit()
                close()
            }
        }
        println("- hibernate dao > insert time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
        sw = Stopwatch.createStarted()
        for (i in 1..10000) {
            sessionFactory.openSession().apply {
                transaction.begin()
                get(HibernateEntity::class.java, i).value
                transaction.commit()
                close()
            }
        }
        println("- hibernate dao > select time: ${sw.elapsed(TimeUnit.MILLISECONDS)}ms")
    }

    private val sessionFactory by lazy {
        val configuration = Configuration()
        configuration.addAnnotatedClass(HibernateEntity::class.java)
        configuration.buildSessionFactory(StandardServiceRegistryBuilder().build())
    }
}

@Entity
class HibernateEntity {
    @Id
    @GeneratedValue
    var id = 0
    var value: Boolean? = null
    var key: Int? = null
}