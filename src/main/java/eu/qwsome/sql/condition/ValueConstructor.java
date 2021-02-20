package eu.qwsome.sql.condition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is useful as a crate object that can contain non null values.
 *
 * @author Lukáš Kvídera
 */
public class ValueConstructor implements Iterable<Object> {

  private final List<Object> values = new ArrayList<>();

  /**
   * Adds a value that will be bound to the query.
   * Method is null safe.
   *
   * @param holder holder that holds a value
   * @return {@link ValueConstructor} with values
   */
  ValueConstructor add(final ValueHolder holder) {
    addValueIfPresent(holder);
    return this;
  }

  /**
   * Adds all values that will be bound to the query.
   * Method is null safe.
   *
   * @param holder holder that holds a value
   * @return {@link ValueConstructor} with values
   */
  public ValueConstructor add(final Iterable<Object> holder) {
    holder.forEach(this.values::add);
    return this;
  }


  private void addValueIfPresent(final ValueHolder holder) {
    final Object value = holder.getValue();
    if (value != null) {
      this.values.add(value);
    }
  }

  /**
   * @return iterator over stored values
   */
  @Override
  public Iterator<Object> iterator() {
    return this.values.iterator();
  }

  /**
   * This method can be used for binding of objects with JdbcTemplate.
   *
   * @return array of defined objects
   */
  public Object[] toArray() {
    return this.values.toArray();
  }
}
