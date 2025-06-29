/*
 * Crate for join attributes.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.condition.Condition
import eu.qwsome.sql.condition.ValueConstructor

abstract class TableJoin(
    private val joinTable: String,
    private val condition: Condition,
) : Join {

    fun get(): CharSequence {
        val builder = StringBuilder()
        appendTo(builder)
        return builder
    }

    /**
     * @return type of join
     */
    abstract fun getPrefix(): CharSequence

    override fun appendTo(builder: StringBuilder) {
        builder.append(getPrefix())
            .append(" JOIN ")
            .append(joinTable)
            .append(" ON ")

        condition.appendTo(builder)
    }

    /**
     * @return variables to be bound in ON clause's conditions
     */
    override fun toValues(): ValueConstructor {
        return condition.getValues()
    }

    /**
     * This enum determines type of join.
     */
    enum class Type {
        LEFT,
        INNER
    }
}
