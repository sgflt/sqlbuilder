/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.api.Appendable

internal class GroupBy(private vararg val columns: Column) : Appendable {

    init {
        require(columns.isNotEmpty()) { "Provide valid columns!" }
    }

    override fun appendTo(builder: StringBuilder) {
        builder.append(" GROUP BY ")
        columns.forEachIndexed { index, column ->
            if (index > 0) {
                builder.append(',')
            }

            builder.append(column.getSql())
        }
    }
}
