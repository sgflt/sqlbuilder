package eu.qwsome.sql.condition;


/**
 * @author Lukáš Kvídera
 */
abstract class BiCondition implements Condition {

  /**
   * The colum on the left side of operator.
   */
  private final ValueHolder first;

  /**
   * The colum on the right side of operator.
   */
  private final ValueHolder second;

  BiCondition(final ValueHolder first, final ValueHolder second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public ValueConstructor getValues() {
    return new ValueConstructor()
      .add(this.first)
      .add(this.second);
  }

  @Override
  public void appendTo(final StringBuilder builder) {
    builder.append(this.first.getSql())
      .append(getOperator())
      .append(this.second.getSql());
  }

  abstract String getOperator();
}
