package eu.qwsome.sql;

import org.junit.jupiter.api.Test;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.Select.select;
import static eu.qwsome.sql.ValueLiteral.value;
import static eu.qwsome.sql.condition.FieldComparator.comparedField;
import static org.assertj.core.api.Assertions.assertThat;

class OrderByWhereTest {

  @Test
  void toSql() {
    final var select = select()
      .from("table t2")
      .where(comparedField(column("t1", "z")).isEqualTo(value("k")))
      .groupBy(column("abc"));

    final var sql = select.toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table t2 WHERE t1.z = ? GROUP BY abc");

    final var values = select.toValues();
    assertThat(values).containsExactly("k");
  }
}
