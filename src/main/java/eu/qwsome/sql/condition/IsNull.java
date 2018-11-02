package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
public class IsNull implements Condition {

  private final ValueHolder column;

  public IsNull(final ValueHolder column) {
    this.column = column;
  }

  @Override
  public CharSequence get() {
    return this.column.get() + " IS NULL";
  }
}
