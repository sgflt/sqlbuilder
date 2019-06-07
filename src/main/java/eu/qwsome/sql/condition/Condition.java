package eu.qwsome.sql.condition;


import eu.qwsome.sql.api.Appendable;

/**
 * @author Lukáš Kvídera
 */
public interface Condition extends Appendable {
  /**
   * This method returns string representation of a condition.
   * x = y
   * x AND y
   * etc.
   *
   * @return string representation
   */
  CharSequence get();

  /**
   * Returns compound condition as a logical AND.
   *
   * @param another condition to be composed
   * @return this AND another
   */
  default Condition and(final Condition another) {
    return new And(this, another);
  }

  /**
   * Returns compound condition as a logical OR.
   *
   * @param another condition to be composed
   * @return this OR another
   */
  default Condition or(final Condition another) {
    return new Or(this, another);
  }

  /**
   * Returns negation of this condition.
   *
   * @return NOT this
   */
  default Condition not() { return null; }

  /**
   * Returns a {@link ValueConstructor} that contains bindable values
   *
   * @return {@link ValueConstructor}
   */
  ValueConstructor getValues();
}
