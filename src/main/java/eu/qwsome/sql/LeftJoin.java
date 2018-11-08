package eu.qwsome.sql;

import eu.qwsome.sql.condition.Condition;

/**
 * Crate for left join attributes.
 *
 * @author Lukáš Kvídera
 */
class LeftJoin extends Join {

  LeftJoin(final String joinTable, final Condition condition) {
    super(joinTable, condition);
  }

  @Override
  CharSequence getPrefix() {
    return " LEFT";
  }
}