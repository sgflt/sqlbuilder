package eu.qwsome.sql;

import eu.qwsome.sql.api.Appendable;
import eu.qwsome.sql.condition.ValueConstructor;

/**
 * @author Lukáš Kvídera
 */
interface Join extends Appendable {
  ValueConstructor toValues();
}
