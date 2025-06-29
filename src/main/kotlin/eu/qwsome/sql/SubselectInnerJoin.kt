/*
 * Crate for join attributes.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.condition.Condition

internal class SubselectInnerJoin(
    subselect: SubselectValueHolder,
    alias: String,
    condition: Condition,
) : SubselectJoin(subselect, alias, condition) {

    override fun getPrefix(): CharSequence = ""
}
