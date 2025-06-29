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
    fun and(another: Condition): ConditionBuilder {
        root = root?.and(another) ?: another
        return this
    }

    /**
     * Appends another condition to current context with OR if applicable.
     *
     * @param another condition to be composed
     * @return this builder
     */
    fun or(another: Condition): ConditionBuilder {
        root = root?.or(another) ?: another
        return this
    }

    /**
     * @return condition or null if empty
     */
    fun build(): Condition? {
        return root
    }

    companion object {
        /**
         * Creates a new instance of [ConditionBuilder]
         *
         * @return [ConditionBuilder]
         */
        @JvmStatic
        fun create(): ConditionBuilder {
            return ConditionBuilder()
        }
    }
}