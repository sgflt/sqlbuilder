package eu.qwsome.sql;

import eu.qwsome.sql.condition.Condition;

/**
 * Crate for join attributes.
 *
 * @author Lukáš Kvídera
 */
class InnerJoin extends Join {

  InnerJoin(final String joinTable, final Condition condition) {
    super(joinTable, condition);
  }

  @Override
  CharSequence getPrefix() {
    return "";
  }
}