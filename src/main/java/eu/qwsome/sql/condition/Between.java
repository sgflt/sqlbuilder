package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
class Between implements Condition {

  private final ValueHolder field;
  private final ValueHolder from;
  private final ValueHolder to;


  Between(final ValueHolder field, final ValueHolder from, final ValueHolder to) {
    this.field = field;
    this.from = from;
    this.to = to;
  }

  @Override
  public CharSequence get() {
    return this.field.getSql() + " BETWEEN " + this.from.getSql() + " AND " + this.to.getSql();
  }

  @Override
  public ValueConstructor getValues() {
    return new ValueConstructor()
      .add(this.field)
      .add(this.from)
      .add(this.to);
  }

}
