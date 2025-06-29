/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

class IsLessOrEqualThan(first: ValueHolder, second: ValueHolder) : BiCondition(first, second) {

    override fun getOperator(): String = " <= "
}
