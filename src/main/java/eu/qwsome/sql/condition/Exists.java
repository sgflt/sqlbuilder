package eu.qwsome.sql.condition;


import eu.qwsome.sql.Select;

import java.util.Arrays;

import static eu.qwsome.sql.SubselectValueHolder.subselect;

public class Exists implements Condition {

  private final ValueHolder[] values;


  Exists(final ValueHolder... values) {
    this.values = values;
  }


  public static Exists exists(final ValueHolder values) {
    return new Exists(values);
  }


  public static Exists exists(final Select.ConditionsBuiltPhase subselect) {
    return new Exists(subselect(subselect));
  }


  @Override
  public CharSequence get() {
    final var builder = new StringBuilder();
    appendTo(builder);

    return builder;
  }


  @Override
  public ValueConstructor getValues() {
    final var valueConstructor = new ValueConstructor();
    Arrays.stream(this.values).forEach(valueConstructor::add);
    return valueConstructor;
  }


  @Override
  public void appendTo(final StringBuilder builder) {
    builder
      .append("EXISTS ");

    int i = 0;
    for (; i < this.values.length - 1; i++) {
      builder.append(this.values[i].getSql())
        .append(", ");
    }

    builder.append(this.values[i].getSql());
  }
}
