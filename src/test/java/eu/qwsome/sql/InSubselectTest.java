package eu.qwsome.sql;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.Select.select;
import static eu.qwsome.sql.ValueLiteral.value;
import static eu.qwsome.sql.condition.FieldComparator.comparedField;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class InSubselectTest {

  @Test
  void toSql() {
    final var select = select()
        .from("table t2")
        .where(comparedField(column("t2", "x"))
            .in(select("t1.abc")
                .from("table t1")
                .where(comparedField(column("t1", "z")).isEqualTo(value("k")))));

    final var sql = select.toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table t2 WHERE t2.x IN ( SELECT t1.abc FROM table t1 WHERE t1.z = ? )");

    final var values = select.toValues();
    assertThat(values).containsExactly("k");
  }


  @Test
  void complicatedSQL() {
    final var select = select()
        .from("table t2")
        .where(
            comparedField(column("t2", "x"))
                .in(
                    select("t1.abc")
                        .from("table t1")
                        .where(comparedField(column("t1", "z")).isEqualTo(value("k"))
                            .and(comparedField(column("t1", "another")).isLessOrEqualThan(value("anotherVal"))))
                )
                .or(comparedField(column("t2", "brm")).isNull())
                .and(comparedField(column("t2", "ca"))
                    .in(
                        select("t3.aby").from("last t3")
                            .where(comparedField(column("t3", "kol"))
                                .in(
                                    select("t4.xaw").from("first t4").where(comparedField(column("t4", "ul")).isNull()))
                            )
                    ))
        );

    final var sql = select.toSql();
    assertThat(sql).isEqualTo("SELECT *"
        + " FROM table t2"
        + " WHERE ( ( t2.x IN ("
        + " SELECT t1.abc FROM table t1 WHERE ( t1.z = ? AND t1.another <= ? "
        + ") )"
        + " OR t2.brm IS NULL )"
        + " AND t2.ca IN ("
        + " SELECT t3.aby FROM last t3 WHERE t3.kol IN ("
        + " SELECT t4.xaw FROM first t4 WHERE t4.ul IS NULL"
        + " ) ) )"
    );

    final var values = select.toValues();
    assertThat(values).containsExactly("k", "anotherVal");
  }
}
