package eu.qwsome.sql;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.Select.select;
import static eu.qwsome.sql.ValueLiteral.value;
import static eu.qwsome.sql.condition.FieldComparator.comparedField;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;

import eu.qwsome.sql.condition.ValueConstructor;
import org.junit.jupiter.api.Test;

class UnionTest {

  @Test
  void toSql() {
    final String sql = Union.of(select().from("x1"), select().from("x2")).toSql();
    assertThat(sql).isEqualTo("SELECT * FROM x1 UNION SELECT * FROM x2");
  }


  @Test
  void toSqlAll() {
    final String sql = Union.allOf(select().from("x1"), select().from("x2")).toSql();
    assertThat(sql).isEqualTo("SELECT * FROM x1 UNION ALL SELECT * FROM x2");
  }


  @Test
  void toValues_empty() {
    final ValueConstructor values = Union.of(
            select().from("x1"),
            select().from("x2")
        )
        .toValues();
    assertThat(values).isEmpty();
  }


  @Test
  void toValues_fluent() {
    final ValueConstructor values =
        select().from("x1")
            .where(
                comparedField(column("z1")).isEqualTo(value("z2"))
            )
            .union(
                select().from("x2").where(comparedField(value("z3")).isEqualTo(value("z4")))
            ).toValues()
        ;

    assertThat(values).containsExactly("z2", "z3", "z4");
  }


  @Test
  void toSql_fluent() {
    final var sql =
        select().from("x1")
            .where(
                comparedField(column("z1")).isEqualTo(value("z2"))
            )
            .union(
                select().from("x2").where(comparedField(value("z3")).isEqualTo(value("z4")))
            ).toSql()
        ;

    assertThat(sql).isEqualTo("SELECT * FROM x1 WHERE z1 = ? UNION SELECT * FROM x2 WHERE ? = ?");
  }


  @Test
  void toSql_paged() {
    final var sql =
        select("FIRST 10 *").from(
            select().from("x1")
                .where(
                    comparedField(column("z1")).isEqualTo(value("z2"))
                )
                .union(
                    select().from("x2").where(comparedField(value("z3")).isEqualTo(value("z4")))
                ),
            "un"

        ).toSql()
        ;

    assertThat(sql).isEqualTo(
        "SELECT FIRST 10 * FROM ( SELECT * FROM x1 WHERE z1 = ? UNION SELECT * FROM x2 WHERE ? = ? ) AS un");
  }


  @Test
  void toValues_paged() {
    final var values =
        select("FIRST 10 *").from(
            select().from("x1")
                .where(
                    comparedField(column("z1")).isEqualTo(value("z2"))
                )
                .union(
                    select().from("x2").where(comparedField(value("z3")).isEqualTo(value("z4")))
                ),
            "un"

        ).toValues()
        ;


    assertThat(values).containsExactly("z2", "z3", "z4");
  }


  @Test
  void tosSql_Filled() {
    final ValueConstructor values = Union.allOf(
        select().from("x1").where(comparedField(column("z1")).isEqualTo(value("z2"))),
        select().from("x2").where(comparedField(value("z3")).isEqualTo(value("z4")))
    ).toValues();
    assertThat(values).first().isEqualTo("z2");
    final Iterator<Object> iterator = values.iterator();
    iterator.next();
    assertThat(iterator.next()).isEqualTo("z3");
    assertThat(iterator.next()).isEqualTo("z4");
    assertThat(iterator).isExhausted();
  }
}
