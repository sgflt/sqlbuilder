package eu.qwsome.sql.condition;


import eu.qwsome.sql.Select;

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
   * Returns a {@link Condition} with meaning this &lt; another.
   *
   * @param another field to be compared
   * @return {@link Condition}
   */
  Condition isLessThan(final ValueHolder another);

  /**
   * Returns a {@link Condition} with meaning this &lt;= another.
   * @param another field to be compared
   * @return {@link Condition}
   */
  Condition isLessOrEqualThan(final ValueHolder another);

  /**
   * Returns a {@link Condition} with meaning this &gt; another.
   *
   * @param another field to be compared
   * @return {@link Condition}
   */
  Condition isGreaterThan(final ValueHolder another);

  /**
   * Returns a {@link Condition} with meaning this &gt;= another.
   * @param another field to be compared
   * @return {@link Condition}
   */
  Condition isGreaterOrEqualThan(final ValueHolder another);

  /**
   * Returns a {@link Condition} checking containment of this in
   * a set of another values.
   *
   * @param another the set of values for comparison
   * @return {@link Condition}
   */
  Condition in(final ValueHolder... another);

  /**
   * Returns a {@link Condition} checking containment of this in
   * a set of values returned by subselect.
   *
   * @param subselect the set of values for comparison
   * @return {@link Condition}
   */
  Condition in(final Select.ConditionsBuiltPhase subselect);

  /**
   * Returns a {@link Condition} checking if this matches a pattern.
   *
   * @param pattern to be matched
   * @return {@link Condition}
   */
  Condition like(final ValueHolder pattern);

  /**
   * Returns a {@link Condition} checking if this does not match a pattern
   * (this is complementary condition to {@link #like(ValueHolder)}).
   *
   * @param pattern to not be matched
   * @return {@link Condition}
   */
  Condition notLike(final ValueHolder pattern);

  /**
   * Returns a {@link Condition} with meaning from &lt;= this &lt;= to.
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
