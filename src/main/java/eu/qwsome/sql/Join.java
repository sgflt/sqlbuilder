package eu.qwsome.sql;

import eu.qwsome.sql.api.Appendable;
import eu.qwsome.sql.condition.Condition;

/**
 * Crate for join attributes.
 *
 * @author Lukáš Kvídera
 */
abstract class Join implements Appendable {

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
    final StringBuilder builder = new StringBuilder();
    appendTo(builder);
    return builder;
  }

  abstract CharSequence getPrefix();

  @Override
  public void appendTo(final StringBuilder builder) {
    builder.append(getPrefix())
      .append(" JOIN ")
      .append(this.joinTable)
      .append(" ON ");

    this.condition.appendTo(builder);
  }


  enum Type {
    LEFT,
    INNER
  }
}