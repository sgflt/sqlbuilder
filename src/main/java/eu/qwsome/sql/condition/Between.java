package eu.qwsome.sql.condition;

import eu.qwsome.sql.Column;

/**
 * @author Lukáš Kvídera
 */
public class Between implements Condition {

  private final Column column;
  private final Column from;
  private final Column to;


  public Between(final Column column, final Column from, final Column to) {
    this.column = column;
    this.from = from;
    this.to = to;
  }

  @Override
  public CharSequence get() {
    return this.column.get() + " BETWEEN " + this.from.get() + " AND " + this.to.get();
  }
}
