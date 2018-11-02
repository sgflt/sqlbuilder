package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
class IsNull extends UnaryCondition {

  IsNull(final ValueHolder field) {
    super(field);
  }

  @Override
  String getSuffix() {
    return " IS NULL";
  }

}
