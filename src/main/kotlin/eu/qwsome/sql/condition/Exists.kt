/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

import eu.qwsome.sql.Select
import eu.qwsome.sql.SubselectValueHolder

class Exists private constructor(private vararg val values: ValueHolder) : Condition {

    override fun getValues(): ValueConstructor {
        val valueConstructor = ValueConstructor()
        values.forEach { valueConstructor.add(it) }
        return valueConstructor
    }

    override fun appendTo(builder: StringBuilder) {
        builder.append("EXISTS ")

        values.forEachIndexed { index, value ->
            if (index > 0) {
                builder.append(", ")
            }
            builder.append(value.getSql())
        }
    }

    companion object {
        @JvmStatic
        fun exists(values: ValueHolder) = Exists(values)

        @JvmStatic
        fun exists(subselect: Select.ConditionsBuiltPhase) = Exists(SubselectValueHolder.subselect(subselect))
    }
}
