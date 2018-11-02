package eu.qwsome.sql.condition;

import eu.qwsome.sql.Column;

/**
 * @author Lukáš Kvídera
 */
public class IsGereaterThan extends BiCondition {
  public IsGereaterThan(final Column column, final Column another) {
    super(column, another);
  }

  @Override
  String getOperator() {
    return " > ";
  }
}
