package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
class IsLessThan extends BiCondition {
  IsLessThan(final ValueHolder column, final ValueHolder another) {
    super(column, another);
  }

  @Override
  String getOperator() {
    return " < ";
  }
}
