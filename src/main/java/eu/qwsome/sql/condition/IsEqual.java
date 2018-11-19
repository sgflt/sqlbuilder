package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
class IsEqual extends BiCondition {
  IsEqual(final ValueHolder first, final ValueHolder second) {
    super(first, second);
  }

  @Override
  String getOperator() {
    return " = ";
  }

}
