package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
class Or extends CompoundCondition {

  Or(final Condition condition, final Condition another) {
    super(condition, another);
  }

  @Override
  String getOperator() {
    return " OR ";
  }
}
