package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
public class IsLessThan extends BiCondition {
  public IsLessThan(final ValueHolder column, final ValueHolder another) {
    super(column, another);
  }

  @Override
  String getOperator() {
    return " < ";
  }
}
