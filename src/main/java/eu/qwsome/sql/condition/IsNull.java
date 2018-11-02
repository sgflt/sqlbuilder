package eu.qwsome.sql.condition;

import eu.qwsome.sql.Column;

/**
 * @author Lukáš Kvídera
 */
public class IsNull implements Condition {

  private final Column column;

  public IsNull(final Column column) {
    this.column = column;
  }

  @Override
  public CharSequence get() {
    return this.column.get() + " IS NULL";
  }
}
