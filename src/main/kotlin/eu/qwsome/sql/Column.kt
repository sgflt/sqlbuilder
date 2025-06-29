/*
 * This class represents a column in a database table.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.condition.ValueHolder
import eu.qwsome.sql.condition.withFunction

class Column private constructor(private val name: String) : ValueHolder {

    override fun getSql(): String = name

    override fun getValue(): String? = null

    override fun apply(functionName: String): ValueHolder = withFunction(functionName)

    companion object {
        /**
         * Constructs column with a name.
         *
         * @param name of column
         * @return a [Column] instance
         */
        @JvmStatic
        fun column(name: String) = Column(name)

        /**
         * Constructs column with name and sourceTableAlias.
         *
         * @param sourceTableAlias alias of table to which the column belongs
         * @param name of column
         */
        @JvmStatic
        fun column(sourceTableAlias: String, name: String) = Column("$sourceTableAlias.$name")
    }
}
