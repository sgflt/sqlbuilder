package eu.qwsome.sql.condition;

/**
 * @author Lukáš Kvídera
 */
public interface ValueHolder {

  /**
   * Returns column name or question mark as a wildcard.
   *
   * @return string representation of value in SQL
   */
  String getSql();

  /**
   * Returns value that can be bound into wildcard, or null for column name.
   *
   * @return value to be bound in prepared statement
   */
  Object getValue();

  /**
   * Applies function to value holder
   *
   * @param functionName
   * @return
   */
  ValueHolder apply(String functionName);
}
