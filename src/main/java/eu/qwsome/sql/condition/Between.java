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
    final StringBuilder builder = new StringBuilder();
    appendTo(builder);
    return builder;
  }

  @Override
  public void appendTo(final StringBuilder builder) {
    builder.append(this.field.getSql())
      .append(" BETWEEN ")
      .append(this.from.getSql())
      .append(" AND ")
      .append(this.to.getSql());
  }

  @Override
  public ValueConstructor getValues() {
    return new ValueConstructor()
      .add(this.field)
      .add(this.from)
      .add(this.to);
  }
}
