/*
 * This class represents composition of two conditions.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

abstract class CompoundCondition(
    private val first: Condition,
    private val second: Condition,
) : Condition {

    override fun getValues(): ValueConstructor {
        return ValueConstructor()
            .add(first.getValues())
            .add(second.getValues())
    }

    override fun appendTo(builder: StringBuilder) {
        builder.append("( ")
        first.appendTo(builder)
        builder.append(getOperator())
        second.appendTo(builder)
        builder.append(" )")
    }

    /**
     * @return string representation of an operator
     * @see And
     * @see Or
     * @see Between
     */
    protected abstract fun getOperator(): String
}
