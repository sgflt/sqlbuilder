package eu.qwsome.sql.condition;


import java.util.Arrays;

public class In implements Condition {

  private final ValueHolder field;
  private final ValueHolder[] values;

  In(final ValueHolder field, final ValueHolder... values){
    this.field = field;
    this.values = values;
  }

  @Override
  public CharSequence get() {
    final StringBuilder builder = new StringBuilder();
    appendTo(builder);

    return builder;
  }

  @Override
  public ValueConstructor getValues() {
    final ValueConstructor valueConstructor = new ValueConstructor().add(this.field);
    Arrays.stream(this.values).forEach(valueConstructor::add);
    return valueConstructor;
  }

  @Override
  public void appendTo(StringBuilder builder) {
    builder.append(this.field.getSql())
      .append(" IN ")
      .append("( ");

    int i = 0;
    for (; i < this.values.length - 1; i++) {
      builder.append(this.values[i].getSql())
        .append(", ");
    }

    builder.append(this.values[i].getSql())
      .append(" )");
  }
}
