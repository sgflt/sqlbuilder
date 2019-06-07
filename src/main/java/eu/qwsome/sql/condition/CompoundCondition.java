package eu.qwsome.sql.condition;

/**
 * This class represents composition of two conditions.
 *
 * @author Lukáš Kvídera
 */
abstract class CompoundCondition implements Condition {
  private final Condition first;
  private final Condition second;

  CompoundCondition(final Condition first, final Condition second) {
    this.first = first;
    this.second = second;
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
