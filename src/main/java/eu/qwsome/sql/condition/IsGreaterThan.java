package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
class IsGreaterThan extends BiCondition {
  IsGreaterThan(final ValueHolder column, final ValueHolder another) {
    super(column, another);
  }

  @Override
  String getOperator() {
    return " > ";
  }
}
