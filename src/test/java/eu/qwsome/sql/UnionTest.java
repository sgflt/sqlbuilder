package eu.qwsome.sql;

import eu.qwsome.sql.condition.ValueConstructor;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.ValueLiteral.value;
import static eu.qwsome.sql.condition.FieldComparator.comparedField;
import static org.assertj.core.api.Assertions.assertThat;

class UnionTest {

  @Test
  void toSql() {
    final String sql = Union.of(Select.select().from("x1"), Select.select().from("x2")).toSql();
    assertThat(sql).isEqualTo("SELECT * FROM x1 UNION SELECT * FROM x2");
  }

  @Test
  void toSqlAll() {
    final String sql = Union.allOf(Select.select().from("x1"), Select.select().from("x2")).toSql();
    assertThat(sql).isEqualTo("SELECT * FROM x1 UNION ALL SELECT * FROM x2");
  }

  @Test
  void toValues_empty() {
    final ValueConstructor values = Union.of(Select.select().from("x1"), Select.select().from("x2")).toValues();
    assertThat(values).isEmpty();
  }

  @Test
  void tosSql_Filled() {
    final ValueConstructor values = Union.allOf(
      Select.select().from("x1").where(comparedField(column("z1")).isEqualTo(value("z2"))),
      Select.select().from("x2").where(comparedField(value("z3")).isEqualTo(value("z4")))).toValues();
    assertThat(values).first().isEqualTo("z2");
    final Iterator<Object> iterator = values.iterator();
    iterator.next();
    assertThat(iterator.next()).isEqualTo("z3");
    assertThat(iterator.next()).isEqualTo("z4");
    assertThat(iterator).isExhausted();
  }

}