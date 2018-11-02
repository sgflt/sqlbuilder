package eu.qwsome.sql.condition;

public interface ComparableField {
  Condition isEqualTo(final ValueHolder another);

  Condition isBetween(final ValueHolder from, final ValueHolder to);

  Condition isLessThan(final ValueHolder another);

  Condition isGreaterThan(final ValueHolder another);

  Condition isNull();

  Condition isNotNull();
}
