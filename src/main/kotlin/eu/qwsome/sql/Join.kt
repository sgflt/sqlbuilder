/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.api.Appendable
import eu.qwsome.sql.condition.ValueConstructor

interface Join : Appendable {
    fun toValues(): ValueConstructor
}