package eu.qwsome.sql;

import eu.qwsome.sql.condition.ComparableField;
import org.junit.jupiter.api.Test;

import java.util.List;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.Select.select;
import static eu.qwsome.sql.ValueLiteral.value;
import static eu.qwsome.sql.condition.FieldComparator.comparedField;
import static org.assertj.core.api.Assertions.assertThat;

class InTest {

  @Test
  void toSql() {
    final var select = select()
      .from("table t2")
      .where(comparedField(column("t2", "x"))
        .in(value("aaa"), value("bbb")));

    final var sql = select.toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table t2 WHERE t2.x IN ( ?, ? )");

    final var values = select.toValues();
    assertThat(values).containsExactly("aaa", "bbb");
  }

  @Test
  void toSqlCollection() {
    final var select = select()
      .from("table t2")
      .where(comparedField(column("t2", "x"))
        .in(List.of(value("aaa"), value("bbb"))));

    final var sql = select.toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table t2 WHERE t2.x IN ( ?, ? )");

    final var values = select.toValues();
    assertThat(values).containsExactly("aaa", "bbb");
  }

  @Test
  void toSqlObjectCollection() {
    final var select = select()
      .from("table t2")
      .where(comparedField(column("t2", "x"))
        .in(ComparableField.toValues(List.of("aaa", 1))));

    final var sql = select.toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table t2 WHERE t2.x IN ( ?, ? )");

    final var values = select.toValues();
    assertThat(values).containsExactly("aaa", 1);
  }
}
