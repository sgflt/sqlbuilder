/*
 * This class represents a literal value in sql.
 *
 * For example: column = 1
 * where 1 is a literal.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.condition.ValueHolder
import eu.qwsome.sql.condition.withFunction

class ValueLiteral private constructor(private val value: Any?) : ValueHolder {

    override fun getSql(): String = "?"

    override fun getValue(): Any? = value

    override fun apply(functionName: String): ValueHolder = withFunction(functionName)

    companion object {
        @JvmStatic
        fun value(value: Any?) = ValueLiteral(value)
    }
}
