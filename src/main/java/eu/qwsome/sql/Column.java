package eu.qwsome.sql;

import eu.qwsome.sql.condition.ValueHolder;


/**
 * This class represents a column in a database table.
 *
 * @author Lukáš Kvídera
 */
public class Column implements ValueHolder {

  private final String name;

  /**
   * Constructs column with a name.
   *
   * @param name of column
   */
  private Column(final String name) {
    this.name = name;
  }

  /**
   * Constructs column with a name.
   *
   * @param name of column
   * @return a {@link Column} instance
   */
  public static Column column(final String name) {
    return new Column(name);
  }

  /**
   * Constructs column with name and source.
   *
   * @param name of column
   * @param source source of the column
   */
  public static Column column(final String name, final String source){
    return new Column(source.concat(".").concat(name));
  }

  @Override
  public String getSql() {
    return this.name;
  }

  @Override
  public String getValue() {
    return null;
  }
}
