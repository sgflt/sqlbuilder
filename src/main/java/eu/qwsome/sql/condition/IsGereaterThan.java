package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
class IsGereaterThan extends BiCondition {
  IsGereaterThan(final ValueHolder column, final ValueHolder another) {
    super(column, another);
  }

  @Override
  String getOperator() {
    return " > ";
  }
}
