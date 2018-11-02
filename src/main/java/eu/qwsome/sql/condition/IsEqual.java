package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
public class IsEqual extends BiCondition implements Condition {
  public IsEqual(final ValueHolder first, final ValueHolder second) {
    super(first, second);
  }

  @Override
  String getOperator() {
    return " = ";
  }

}
