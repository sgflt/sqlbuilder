package eu.qwsome.sql.condition;


import eu.qwsome.sql.Column;

/**
 * @author Lukáš Kvídera
 */
abstract class BiCondition implements Condition {

  private final Column first;
  private final Column second;

  BiCondition(final Column first, final Column second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public CharSequence get() {
    return this.first.get() + getOperator() + this.second.get();
  }

  abstract String getOperator();
}
