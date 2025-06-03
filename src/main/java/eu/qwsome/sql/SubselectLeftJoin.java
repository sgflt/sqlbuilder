package eu.qwsome.sql;

import eu.qwsome.sql.condition.Condition;

/**
 * Crate for join attributes.
 *
 * @author Lukáš Kvídera
 */
class SubselectLeftJoin extends SubselectJoin {

  SubselectLeftJoin(final SubselectValueHolder subselect, final String alias, final Condition condition) {
    super(subselect, alias, condition);
  }


  @Override
  CharSequence getPrefix() {
    return " LEFT";
  }
}
