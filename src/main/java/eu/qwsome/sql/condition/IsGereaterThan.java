package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
public class IsGereaterThan extends BiCondition {
  public IsGereaterThan(final ValueHolder column, final ValueHolder another) {
    super(column, another);
  }

  @Override
  String getOperator() {
    return " > ";
  }
}
