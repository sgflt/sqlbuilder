package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
class And extends CompoundCondition {

  And(final Condition first, final Condition second) {
    super(first, second);
  }

  @Override
  String getOperator() {
    return " AND ";
  }

}
