package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
public class Between implements Condition {

  private final ValueHolder column;
  private final ValueHolder from;
  private final ValueHolder to;


  public Between(final ValueHolder column, final ValueHolder from, final ValueHolder to) {
    this.column = column;
    this.from = from;
    this.to = to;
  }

  @Override
  public CharSequence get() {
    return this.column.get() + " BETWEEN " + this.from.get() + " AND " + this.to.get();
  }
}
