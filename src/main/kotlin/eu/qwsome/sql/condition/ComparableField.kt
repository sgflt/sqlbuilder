/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

import eu.qwsome.sql.Select
import eu.qwsome.sql.ValueLiteral

interface ComparableField {

    /**
     * Returns a [Condition] with meaning this == another.
     *
     * @param another field to be compared
     * @return [Condition]
     */
    fun isEqualTo(another: ValueHolder): Condition

    /**
     * Returns a [Condition] with meaning this != another.
     *
     * @param another field to be compared
     * @return [Condition]
     */
    fun isNotEqualTo(another: ValueHolder): Condition

    /**
     * Returns a [Condition] with meaning this < another.
     *
     * @param another field to be compared
     * @return [Condition]
     */
    fun isLessThan(another: ValueHolder): Condition

    /**
     * Returns a [Condition] with meaning this <= another.
     *
     * @param another field to be compared
     * @return [Condition]
     */
    fun isLessOrEqualThan(another: ValueHolder): Condition

    /**
     * Returns a [Condition] with meaning this > another.
     *
     * @param another field to be compared
     * @return [Condition]
     */
    fun isGreaterThan(another: ValueHolder): Condition

    /**
     * Returns a [Condition] with meaning this >= another.
     *
     * @param another field to be compared
     * @return [Condition]
     */
    fun isGreaterOrEqualThan(another: ValueHolder): Condition

    /**
     * Returns a [Condition] checking containment of this in
     * a set of another values.
     *
     * @param another the set of values for comparison
     * @return [Condition]
     */
    fun `in`(vararg another: ValueHolder): Condition

    /**
     * Returns a [Condition] checking containment of this in
     * a set of another values.
     *
     * @param another the set of values for comparison
     * @return [Condition]
     */
    fun `in`(another: Collection<ValueHolder>): Condition

    /**
     * Returns a [Condition] checking containment of this in
     * a set of values returned by subselect.
     *
     * @param subselect the set of values for comparison
     * @return [Condition]
     */
    fun `in`(subselect: Select.ConditionsBuiltPhase): Condition

    /**
     * Returns a [Condition] checking if this matches a pattern.
     *
     * @param pattern to be matched
     * @return [Condition]
     */
    fun like(pattern: ValueHolder): Condition

    /**
     * Returns a [Condition] checking if this does not match a pattern
     * (this is complementary condition to [like]).
     *
     * @param pattern to not be matched
     * @return [Condition]
     */
    fun notLike(pattern: ValueHolder): Condition

    /**
     * Returns a [Condition] with meaning from <= this <= to.
     *
     * @param from lower boundary
     * @param to   upper boundary
     * @return [Condition]
     */
    fun isBetween(from: ValueHolder, to: ValueHolder): Condition

    /**
     * Returns a [Condition] with meaning this == null.
     *
     * @return [Condition]
     */
    fun isNull(): Condition

    /**
     * Returns a [Condition] with meaning this != null.
     *
     * @return [Condition]
     */
    fun isNotNull(): Condition

    companion object {
        /**
         * Converts plain objects to ValueLiterals
         *
         * @param objectValues to convert
         * @return converted ValueLiterals
         */
        @JvmStatic
        fun toValues(objectValues: Collection<Any>): Collection<ValueHolder> {
            return objectValues.map { ValueLiteral.value(it) }
        }
    }
}