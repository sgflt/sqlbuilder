/*
 * Crate for join attributes.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.condition.Condition
import eu.qwsome.sql.condition.ValueConstructor

abstract class SubselectJoin(
    private val subselect: SubselectValueHolder,
    private val alias: String,
    private val condition: Condition,
) : Join {

    fun get(): CharSequence {
        return buildString {
            appendTo(this)
        }
    }

    override fun appendTo(builder: StringBuilder) {
        builder.append(getPrefix())
            .append(" JOIN ")
            .append(subselect.getSql())
            .append(' ')
            .append(alias)
            .append(" ON ")

        condition.appendTo(builder)
    }

    /**
     * @return variables to be bound in ON clause's conditions
     */
    override fun toValues(): ValueConstructor {
        return ValueConstructor()
            .add(subselect)
            .add(condition.getValues())
    }

    /**
     * @return type of join
     */
    abstract fun getPrefix(): CharSequence

    /**
     * This enum determines type of join.
     */
    enum class Type {
        LEFT,
        INNER
    }
}
