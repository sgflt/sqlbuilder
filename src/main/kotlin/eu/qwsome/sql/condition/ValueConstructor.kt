/*
 * This class is useful as a crate object that can contain non null values.
 *
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

class ValueConstructor : Iterable<Any> {

    private val values = mutableListOf<Any>()

    /**
     * Adds a value that will be bound to the query.
     * Method is null safe.
     *
     * @param holder holder that holds a value
     * @return [ValueConstructor] with values
     */
    fun add(holder: ValueHolder): ValueConstructor {
        addValueIfPresent(holder)
        return this
    }

    /**
     * Adds all values that will be bound to the query.
     * Method is null safe.
     *
     * @param holder holder that holds a value
     * @return [ValueConstructor] with values
     */
    fun add(holder: Iterable<Any>): ValueConstructor {
        values.addAll(holder)
        return this
    }

    private fun addValueIfPresent(holder: ValueHolder) {
        val value = holder.getValue()
        // subselect can have multiple conditions aggregated in ValueConstructor
        when (value) {
            is ValueConstructor -> values.addAll(value)

            null -> Unit

            else -> values.add(value)
        }
    }

    /**
     * @return iterator over stored values
     */
    override fun iterator(): Iterator<Any> = values.iterator()

    /**
     * This method can be used for binding of objects with JdbcTemplate.
     *
     * @return array of defined objects
     */
    fun toArray(): Array<Any> {
        return values.toTypedArray()
    }
}
