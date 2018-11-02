package eu.qwsome.sql.condition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lukáš Kvídera
 */
public class ValueConstructor implements Iterable<Object> {

  private final List<Object> values = new ArrayList<>();

  ValueConstructor add(final ValueHolder holder) {
    addValueIfPresent(holder);
    return this;
  }

  ValueConstructor add(final Iterable<Object> holder) {
    holder.forEach(this.values::add);
    return this;
  }

  private void addValueIfPresent(final ValueHolder holder) {
    final Object value = holder.getValue();
    if (value != null) {
      this.values.add(value);
    }
  }

  @Override
  public Iterator<Object> iterator() {
    return this.values.iterator();
  }

  public Object[] toArray() {
    return this.values.toArray();
  }
}
