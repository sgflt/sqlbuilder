/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

import eu.qwsome.sql.Select
import eu.qwsome.sql.SubselectValueHolder

class ComparableFieldImpl(private val comparedField: ValueHolder) : ComparableField {

    override fun isEqualTo(another: ValueHolder): Condition =
        IsEqual(comparedField, another)

    override fun isNotEqualTo(another: ValueHolder): Condition =
        IsNotEqual(comparedField, another)

    override fun isBetween(from: ValueHolder, to: ValueHolder): Condition =
        Between(comparedField, from, to)

    override fun isNull(): Condition = IsNull(comparedField)

    override fun isLessThan(another: ValueHolder): Condition =
        IsLessThan(comparedField, another)

    override fun isLessOrEqualThan(another: ValueHolder): Condition =
        IsLessOrEqualThan(comparedField, another)

    override fun isNotNull(): Condition =
        IsNotNull(comparedField)

    override fun isGreaterThan(another: ValueHolder): Condition =
        IsGreaterThan(comparedField, another)

    override fun isGreaterOrEqualThan(another: ValueHolder): Condition =
        IsGreaterOrEqualThan(comparedField, another)

    override fun `in`(vararg another: ValueHolder): Condition =
        In(comparedField, *another)

    override fun `in`(another: Collection<ValueHolder>): Condition =
        In(comparedField, *another.toTypedArray())

    override fun `in`(subselect: Select.ConditionsBuiltPhase): Condition =
        In(comparedField, SubselectValueHolder.subselect(subselect))

    override fun like(pattern: ValueHolder): Condition =
        Like(comparedField, pattern)

    override fun notLike(pattern: ValueHolder): Condition =
        NotLike(comparedField, pattern)
}
