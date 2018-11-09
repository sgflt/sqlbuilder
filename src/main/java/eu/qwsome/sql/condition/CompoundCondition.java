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
    final StringBuilder builder = new StringBuilder();
    appendTo(builder);
    return builder;
  }

  @Override
  public ValueConstructor getValues() {
    return new ValueConstructor()
      .add(this.first.getValues())
      .add(this.second.getValues());
  }

  @Override
  public void appendTo(final StringBuilder builder) {
    builder.append('(');
    this.first.appendTo(builder);
    builder.append(getOperator());
    this.second.appendTo(builder);
    builder.append(')');
  }

  /**
   * @return string representation of an operator
   * @see And
   * @see Or
   * @see Between
   */
  abstract String getOperator();
}
