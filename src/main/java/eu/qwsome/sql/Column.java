package eu.qwsome.sql;

import eu.qwsome.sql.condition.ValueHolder;

/**
 * This class represents a column in a database table.
 *
 * @author Lukáš Kvídera
 */
public class Column implements ValueHolder {

  private final String name;

  private Column(final String name) {
    this.name = name;
  }

  public static Column column(final String name) {
    return new Column(name);
  }

  public String get() {
    return this.name;
  }

}
