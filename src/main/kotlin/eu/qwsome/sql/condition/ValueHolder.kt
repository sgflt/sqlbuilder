/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

interface ValueHolder {
    /**
     * Returns column name or question mark as a wildcard.
     *
     * @return string representation of value in SQL
     */
    fun getSql(): String

    /**
     * Returns value that can be bound into wildcard, or null for column name.
     *
     * @return value to be bound in prepared statement
     */
    fun getValue(): Any?

    /**
     * Applies function to value holder
     *
     * @param functionName the function name to apply
     * @return value holder with function applied
     */
    fun apply(functionName: String): ValueHolder
}

/**
 * Extension function to wrap a ValueHolder with a function call.
 * This replaces the need for FunctionWrappedHolder class.
 *
 * @param functionName the function name to apply
 * @return value holder with function applied
 */
fun ValueHolder.withFunction(functionName: String): ValueHolder = object : ValueHolder {
    override fun getSql(): String = "$functionName(${this@withFunction.getSql()})"
    override fun getValue(): Any? = this@withFunction.getValue()
    override fun apply(functionName: String): ValueHolder = this@withFunction.withFunction(functionName)
}
