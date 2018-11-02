package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
abstract class CompoundCondition implements Condition {
  private final Condition first;
  private final Condition second;

  CompoundCondition(final Condition first, final Condition second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Creates a string representation of actual conditions and wraps them int brackets to preserve meaning and precedence of
   * logical operators.
   *
   * @return string representation of compound condition
   */
  @Override
  public CharSequence get() {
    return "(" + this.first.get() + getOperator() + this.second.get() + ")";
  }

  /**
   * @return string representation of an operator
   * @see And
   * @see Or
   * @see Between
   */
  abstract String getOperator();
}
