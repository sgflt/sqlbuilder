/*
 * Crate for join attributes.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.condition.Condition

internal class InnerTableJoin(
    joinTable: String,
    condition: Condition,
) : TableJoin(joinTable, condition) {

    override fun getPrefix(): CharSequence = ""
}
