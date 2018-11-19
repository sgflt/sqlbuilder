package eu.qwsome.sql.condition;

public interface ComparableField {

  /**
   * Returns a {@link Condition} with meaning this == another.
   *
   * @param another field to be compared
   * @return {@link Condition}
   */
  Condition isEqualTo(final ValueHolder another);

  /**
   * Returns a {@link Condition} with meaning this != another.
   *
   * @param another field to be compared
   * @return {@link Condition}
   */
  Condition isNotEqualTo(ValueHolder another);

  /**
   * Returns a {@link Condition} with meaning this < another.
   *
   * @param another field to be compared
   * @return {@link Condition}
   */
  Condition isLessThan(final ValueHolder another);

  /**
   * Returns a {@link Condition} with meaning this > another.
   *
   * @param another field to be compared
   * @return {@link Condition}
   */
  Condition isGreaterThan(final ValueHolder another);

  /**
   * Returns a {@link Condition} with meaning from <= this <= to.
   *
   * @param from lower boundary
   * @param to   upper boundary
   * @return {@link Condition}
   */
  Condition isBetween(final ValueHolder from, final ValueHolder to);

  /**
   * Returns a {@link Condition} with meaning this == null.
   *
   * @return {@link Condition}
   */
  Condition isNull();

  /**
   * Returns a {@link Condition} with meaning this == another.
   *
   * @return {@link Condition}
   */
  Condition isNotNull();
}
