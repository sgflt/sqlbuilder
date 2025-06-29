/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

class In(
    private val field: ValueHolder,
    private vararg val values: ValueHolder,
) : Condition {

    override fun getValues(): ValueConstructor {
        val valueConstructor = ValueConstructor().add(field)
        values.forEach { valueConstructor.add(it) }
        return valueConstructor
    }

    override fun appendTo(builder: StringBuilder) {
        builder.append(field.getSql())
            .append(" IN ( ")

        values.forEachIndexed { index, value ->
            if (index > 0) {
                builder.append(", ")
            }
            builder.append(value.getSql())
        }

        builder.append(" )")
    }
}
