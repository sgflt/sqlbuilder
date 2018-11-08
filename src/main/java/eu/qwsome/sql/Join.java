package eu.qwsome.sql;

import eu.qwsome.sql.condition.Condition;

/**
 * Crate for join attributes.
 *
 * @author Lukáš Kvídera
 */
abstract class Join {

  /**
   * Table used in JOIN clause.
   */
  private final String joinTable;

  /**
   * Condition in ON clause.
   */
  private final Condition condition;

  Join(final String joinTable, final Condition condition) {
    this.joinTable = joinTable;
    this.condition = condition;
  }

  public CharSequence get() {
    return getPrefix() + " JOIN " + this.joinTable + " ON " + this.condition.get();
  }

  abstract CharSequence getPrefix();


  enum Type {
    LEFT,
    INNER
  }
}