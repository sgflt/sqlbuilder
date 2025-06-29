/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

class And(first: Condition, second: Condition) : CompoundCondition(first, second) {

    override fun getOperator(): String = " AND "
}
