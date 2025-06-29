/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql

import eu.qwsome.sql.condition.ValueConstructor

interface ValueBinding : Query {
    /**
     * @return iterable collection of bindable values
     */
    fun toValues(): ValueConstructor
}