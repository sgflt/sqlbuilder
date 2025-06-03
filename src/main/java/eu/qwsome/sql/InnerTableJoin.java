package eu.qwsome.sql;

import eu.qwsome.sql.condition.Condition;

/**
 * Crate for join attributes.
 *
 * @author Lukáš Kvídera
 */
class InnerTableJoin extends TableJoin {

  InnerTableJoin(final String joinTable, final Condition condition) {
    super(joinTable, condition);
  }

  @Override
  CharSequence getPrefix() {
    return "";
  }
}
