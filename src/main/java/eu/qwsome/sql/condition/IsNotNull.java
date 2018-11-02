package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
class IsNotNull extends UnaryCondition {

  IsNotNull(final ValueHolder field) {
    super(field);
  }

  @Override
  String getSuffix() {
    return " IS NOT NULL";
  }

}
