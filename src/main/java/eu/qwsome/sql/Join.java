package eu.qwsome.sql;

import eu.qwsome.sql.api.Appendable;
import eu.qwsome.sql.condition.Condition;
import eu.qwsome.sql.condition.ValueConstructor;

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

  /**
   * @return tyoe if join
   */
  abstract CharSequence getPrefix();

  @Override
  public void appendTo(final StringBuilder builder) {
    builder.append(getPrefix())
      .append(" JOIN ")
      .append(this.joinTable)
      .append(" ON ");

    this.condition.appendTo(builder);
  }

  /**
   * @return variables to be bound in ON clause's conditions
   */
  ValueConstructor toValues() {
    return this.condition.getValues();
  }

  /**
   * This enum determines type of join.
   */
  enum Type {
    LEFT,
    INNER
  }
}