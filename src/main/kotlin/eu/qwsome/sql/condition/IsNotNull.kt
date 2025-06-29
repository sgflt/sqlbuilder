/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

class IsNotNull(field: ValueHolder) : UnaryCondition(field) {

    override fun getSuffix(): String = " IS NOT NULL"
}
