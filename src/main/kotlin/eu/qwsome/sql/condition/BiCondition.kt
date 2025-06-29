/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

abstract class BiCondition(
    /**
     * The column on the left side of operator.
     */
    private val first: ValueHolder,
    /**
     * The column on the right side of operator.
     */
    private val second: ValueHolder,
) : Condition {

    override fun getValues(): ValueConstructor = ValueConstructor().addAll(first, second)

    override fun appendTo(builder: StringBuilder) {
        builder.append(first.getSql())
            .append(getOperator())
            .append(second.getSql())
    }

    protected abstract fun getOperator(): String
}
