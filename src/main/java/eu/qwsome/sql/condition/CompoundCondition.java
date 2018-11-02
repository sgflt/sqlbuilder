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

  @Override
  public CharSequence get() {
    return "(" + this.first.get() + getOperator() + this.second.get() + ")";
  }

  abstract String getOperator();
}
