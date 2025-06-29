/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

class IsNull(field: ValueHolder) : UnaryCondition(field) {

    override fun getSuffix(): String = " IS NULL"
}
