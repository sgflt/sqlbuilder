package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
abstract class UnaryCondition implements Condition {

  private final ValueHolder field;

  UnaryCondition(final ValueHolder column) {
    this.field = column;
  }

  @Override
  public ValueConstructor getValues() {
    return new ValueConstructor().add(this.field);
  }


  @Override
  public CharSequence get() {
    return this.field.getSql() + getSuffix();
  }

  abstract String getSuffix();
}
