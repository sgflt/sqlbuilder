/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

abstract class UnaryCondition(private val field: ValueHolder) : Condition {

    override fun getValues(): ValueConstructor {
        return ValueConstructor().add(field)
    }

    override fun appendTo(builder: StringBuilder) {
        builder.append(field.getSql())
            .append(getSuffix())
    }

    protected abstract fun getSuffix(): String
}