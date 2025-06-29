/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

import eu.qwsome.sql.api.Appendable

interface Condition : Appendable {
    /**
     * This method returns string representation of a condition.
     * x = y
     * x AND y
     * etc.
     *
     * @return string representation
     */
    fun get(): CharSequence = buildString {
        appendTo(this)
    }

    /**
     * Returns compound condition as a logical AND.
     *
     * @param another condition to be composed
     * @return this AND another
     */
    fun and(another: Condition): Condition = And(this, another)

    /**
     * Returns compound condition as a logical OR.
     *
     * @param another condition to be composed
     * @return this OR another
     */
    fun or(another: Condition): Condition = Or(this, another)

    /**
     * Returns negation of this condition.
     *
     * @return NOT this
     */
    operator fun not(): Condition = Not(this)

    /**
     * Returns a [ValueConstructor] that contains bindable values
     *
     * @return [ValueConstructor]
     */
    fun getValues(): ValueConstructor
}
