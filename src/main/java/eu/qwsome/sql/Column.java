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
   * Constructs column with name and sourceTableAlias.
   *
   * @param name             of column
   * @param sourceTableAlias alias of table to which the column belongs
   */
  public static Column column(final String sourceTableAlias, final String name) {
    return new Column(sourceTableAlias.concat(".").concat(name));
  }

  @Override
  public String getSql() {
    return this.name;
  }

  @Override
  public String getValue() {
    return null;
  }

  @Override
  public ValueHolder apply(final String functionName) {
    return new FunctionWrappedHolder(functionName, this);
  }
}
