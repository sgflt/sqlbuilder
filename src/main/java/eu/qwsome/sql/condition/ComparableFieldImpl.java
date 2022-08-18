package eu.qwsome.sql.condition;

import static eu.qwsome.sql.SubselectValueHolder.subselect;

import eu.qwsome.sql.Select;

/**
 * @author Lukáš Kvídera
 */
public class ComparableFieldImpl implements ComparableField {

  private final ValueHolder comparedField;

  public ComparableFieldImpl(final ValueHolder comparedField) {
    this.comparedField = comparedField;
  }

  @Override
  public Condition isEqualTo(final ValueHolder another) {
    return new IsEqual(this.comparedField, another);
  }

  @Override
  public Condition isNotEqualTo(final ValueHolder another) {
    return new IsNotEqual(this.comparedField, another);
  }

  @Override
  public Condition isBetween(final ValueHolder from, final ValueHolder to) {
    return new Between(this.comparedField, from, to);
  }

  @Override
  public Condition isNull() {
    return new IsNull(this.comparedField);
  }

  @Override
  public Condition isLessThan(final ValueHolder another) {
    return new IsLessThan(this.comparedField, another);
  }

  @Override
  public Condition isLessOrEqualThan(ValueHolder another) {
    return new IsLessOrEqualThan(this.comparedField, another);
  }

  @Override
  public Condition isNotNull() {
    return new IsNotNull(this.comparedField);
  }

  @Override
  public Condition isGreaterThan(final ValueHolder another) {
    return new IsGreaterThan(this.comparedField, another);
  }

  @Override
  public Condition isGreaterOrEqualThan(ValueHolder another) {
    return new IsGreaterOrEqualThan(this.comparedField, another);
  }

  @Override
  public Condition in(ValueHolder... another) {
    return new In(this.comparedField, another);
  }

  @Override
  public Condition in(Select.ConditionsBuiltPhase subselect) {
    return new In(this.comparedField, subselect(subselect));
  }

  @Override
  public Condition like(ValueHolder pattern) {
    return new Like(this.comparedField, pattern);
  }

  @Override
  public Condition notLike(ValueHolder pattern) {
    return new NotLike(this.comparedField, pattern);
  }

}
