package eu.qwsome.sql;

import eu.qwsome.sql.condition.ValueConstructor;

/**
 * @author Lukáš Kvídera
 */
public interface ValueBinding extends Query {
  /**
   * @return iterable collection of bindable values
   */
  ValueConstructor toValues();
}
