/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

class Not(private val condition: Condition) : Condition {

    override fun getValues(): ValueConstructor = condition.getValues()

    override fun appendTo(builder: StringBuilder) {
        builder.append("NOT ")
        condition.appendTo(builder)
    }
}
