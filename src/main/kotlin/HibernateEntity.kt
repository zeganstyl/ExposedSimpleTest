import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue

/**
 * The Class Skill.
 */
@Entity
class HibernateEntity {
    @Id
    @GeneratedValue
    var id = 0
    var value: Boolean? = null
    var key: Int? = null
}