package eu.qwsome.sql;

import eu.qwsome.sql.condition.ValueHolder;

/**
 * @author Lukáš Kvídera
 */
public class ValueLiteral implements ValueHolder {

  private final Object value;

  private ValueLiteral(final Object value) {
    this.value = value;
  }

  public static ValueLiteral value(Object value) {
    return new ValueLiteral(value);
  }

  @Override
  public String get() {
    return "?";
  }
}
