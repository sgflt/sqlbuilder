package eu.qwsome.sql;

import org.junit.jupiter.api.Test;

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
}