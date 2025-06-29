/*
 * @author Lukáš Kvídera
 */
package eu.qwsome.sql.condition

import eu.qwsome.sql.Select
import eu.qwsome.sql.SubselectValueHolder

class ComparableFieldImpl(private val comparedField: ValueHolder) : ComparableField {

    override fun isEqualTo(another: ValueHolder): Condition {
        return IsEqual(comparedField, another)
    }

    override fun isNotEqualTo(another: ValueHolder): Condition {
        return IsNotEqual(comparedField, another)
    }

    override fun isBetween(from: ValueHolder, to: ValueHolder): Condition {
        return Between(comparedField, from, to)
    }

    override fun isNull(): Condition {
        return IsNull(comparedField)
    }

    override fun isLessThan(another: ValueHolder): Condition {
        return IsLessThan(comparedField, another)
    }

    override fun isLessOrEqualThan(another: ValueHolder): Condition {
        return IsLessOrEqualThan(comparedField, another)
    }

    override fun isNotNull(): Condition {
        return IsNotNull(comparedField)
    }

    override fun isGreaterThan(another: ValueHolder): Condition {
        return IsGreaterThan(comparedField, another)
    }

    override fun isGreaterOrEqualThan(another: ValueHolder): Condition {
        return IsGreaterOrEqualThan(comparedField, another)
    }

    override fun `in`(vararg another: ValueHolder): Condition {
        return In(comparedField, *another)
    }

    override fun `in`(another: Collection<ValueHolder>): Condition {
        return In(comparedField, *another.toTypedArray())
    }

    override fun `in`(subselect: Select.ConditionsBuiltPhase): Condition {
        return In(comparedField, SubselectValueHolder.subselect(subselect))
    }

    override fun like(pattern: ValueHolder): Condition {
        return Like(comparedField, pattern)
    }

    override fun notLike(pattern: ValueHolder): Condition {
        return NotLike(comparedField, pattern)
    }
}