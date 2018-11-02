package eu.qwsome.sql.condition;

import eu.qwsome.sql.Column;

/**
 * @author Lukáš Kvídera
 */
public class IsNotNull implements Condition {

  private final Column column;

  public IsNotNull(final Column column) {
    this.column = column;
  }

  @Override
  public CharSequence get() {
    return this.column.get() + " IS NOT NULL";
  }
}
