/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

class NotLike(first: ValueHolder, second: ValueHolder) : BiCondition(first, second) {

    override fun getOperator(): String = " NOT LIKE "
}
