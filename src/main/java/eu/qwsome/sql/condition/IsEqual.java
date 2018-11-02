package eu.qwsome.sql.condition;

import eu.qwsome.sql.Column;

/**
 * @author Lukáš Kvídera
 */
public class IsEqual extends BiCondition implements Condition {
  public IsEqual(final Column first, final Column second) {
    super(first, second);
  }

  @Override
  String getOperator() {
    return " = ";
  }

}
