/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

class Between(
    private val field: ValueHolder,
    private val from: ValueHolder,
    private val to: ValueHolder,
) : Condition {

    override fun appendTo(builder: StringBuilder) {
        builder.append(field.getSql())
            .append(" BETWEEN ")
            .append(from.getSql())
            .append(" AND ")
            .append(to.getSql())
    }

    override fun getValues(): ValueConstructor {
        return ValueConstructor().addAll(field, from, to)
    }
}
