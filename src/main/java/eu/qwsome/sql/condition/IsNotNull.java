package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
public class IsNotNull implements Condition {

  private final ValueHolder column;

  public IsNotNull(final ValueHolder column) {
    this.column = column;
  }

  @Override
  public CharSequence get() {
    return this.column.get() + " IS NOT NULL";
  }
}
