package eu.qwsome.sql.condition;

import eu.qwsome.sql.Column;

/**
 * @author Lukáš Kvídera
 */
public class IsNotEqual extends BiCondition {
  public IsNotEqual(final Column column, final Column another) {
    super(column, another);
  }

  @Override
  String getOperator() {
    return " < ";
  }
}
