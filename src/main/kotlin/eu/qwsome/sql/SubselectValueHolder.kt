/*
 * This class represents a subselect value in sql.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.condition.ValueHolder
import eu.qwsome.sql.condition.withFunction

class SubselectValueHolder private constructor(private val value: Select.ConditionsBuiltPhase) : ValueHolder {

    override fun getSql(): String {
        return "(${value.toSql()})"
    }

    override fun getValue(): Any? {
        return value.toValues()
    }

    override fun apply(functionName: String): ValueHolder = withFunction(functionName)

    companion object {
        @JvmStatic
        fun subselect(value: Select.ConditionsBuiltPhase): SubselectValueHolder {
            return SubselectValueHolder(value)
        }
    }
}
