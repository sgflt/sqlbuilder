package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
class IsNotEqual extends BiCondition {
  public IsNotEqual(final ValueHolder first, final ValueHolder second) {
    super(first, second);
  }

  @Override
  String getOperator() {
    return " <> ";
  }
}
