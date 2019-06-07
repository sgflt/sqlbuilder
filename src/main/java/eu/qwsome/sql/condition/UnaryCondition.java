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
  public void appendTo(final StringBuilder builder) {
    builder.append(this.field.getSql())
      .append(getSuffix());
  }

  abstract String getSuffix();
}
