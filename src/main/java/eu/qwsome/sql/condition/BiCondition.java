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
  public CharSequence get() {
    return this.first.get() + getOperator() + this.second.get();
  }

  abstract String getOperator();
}
