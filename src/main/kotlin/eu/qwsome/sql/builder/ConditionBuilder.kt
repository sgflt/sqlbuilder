/*
 * This builder uses left associativity when appending conditions.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.builder

import eu.qwsome.sql.condition.Condition

class ConditionBuilder private constructor() {

    private var root: Condition? = null

    /**
     * Appends another condition to current context with AND if applicable.
     *
     * @param another condition to be composed
     * @return this builder
     */
    fun and(another: Condition): ConditionBuilder = apply {
        root = root?.and(another) ?: another
    }

    /**
     * Appends another condition to current context with OR if applicable.
     *
     * @param another condition to be composed
     * @return this builder
     */
    fun or(another: Condition): ConditionBuilder = apply {
        root = root?.or(another) ?: another
    }

    /**
     * @return condition or null if empty
     */
    fun build(): Condition? = root

    companion object {
        /**
         * Creates a new instance of [ConditionBuilder]
         *
         * @return [ConditionBuilder]
         */
        @JvmStatic
        fun create(): ConditionBuilder = ConditionBuilder()
    }
}
