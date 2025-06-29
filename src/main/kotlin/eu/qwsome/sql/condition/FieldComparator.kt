/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

object FieldComparator {

    @JvmStatic
    fun comparedField(value: ValueHolder): ComparableField = ComparableFieldImpl(value)
}
