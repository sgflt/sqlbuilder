/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

class Or(condition: Condition, another: Condition) : CompoundCondition(condition, another) {

    override fun getOperator(): String = " OR "
}
