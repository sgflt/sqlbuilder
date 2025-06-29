/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.condition.ValueConstructor

class Union(
    private val queries: Array<ValueBinding>,
    private val all: Boolean,
) : ValueBinding {

    override fun toSql(): String {
        val keyword = if (all) " UNION ALL " else " UNION "
        return queries.joinToString(keyword) { it.toSql() }
    }

    override fun toValues(): ValueConstructor {
        val values = ValueConstructor()
        queries.map { it.toValues() }.forEach { values.add(it) }
        return values
    }

    companion object {
        /**
         * Creates UNION clause.
         *
         * @param queries that will be used in UNION
         * @return the union clause
         */
        @JvmStatic
        fun of(vararg queries: ValueBinding): Union {
            return Union(arrayOf(*queries), false)
        }

        /**
         * Creates UNION ALL clause.
         *
         * @param queries that will be used in UNION without distinct values
         * @return the union all clause
         */
        @JvmStatic
        fun allOf(vararg queries: ValueBinding): Union {
            return Union(arrayOf(*queries), true)
        }
    }
}
