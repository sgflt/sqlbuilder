/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.api.Appendable

internal class OrderBy(private val columns: Array<Column>) : Appendable {

    init {
        require(columns.isNotEmpty()) { "Provide valid columns!" }
    }

    override fun appendTo(builder: StringBuilder) {
        builder.append(" ORDER BY ")
        columns.map { it.getSql() }
            .forEachIndexed { index, columnSql ->
                if (index > 0) {
                    builder.append(',')
                }
                builder.append(columnSql)
            }
    }
}