package eu.qwsome.sql;

import eu.qwsome.sql.condition.Condition;
import eu.qwsome.sql.condition.ValueConstructor;
import org.junit.jupiter.api.Test;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.Select.select;
import static eu.qwsome.sql.ValueLiteral.value;
import static eu.qwsome.sql.condition.FieldComparator.comparedField;
import static org.assertj.core.api.Assertions.assertThat;

public class SelectTest {

  @Test
  public void testSimpleSelect() {
    final String sql = select().from("table").toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table");
  }

  @Test
  public void testSelectWithColumn() {
    final String sql = select("column1").from("table").toSql();
    assertThat(sql).isEqualTo("SELECT column1 FROM table");
  }

  @Test
  public void testSelectWithColumns() {
    final String sql = select("column1", "column2").from("table").toSql();
    assertThat(sql).isEqualTo("SELECT column1,column2 FROM table");
  }

  @Test
  public void testSimpleSelect_SingleCondition() {
    final String sql = select().from("table").where(comparedField(column("column1")).isEqualTo(column("column2"))).toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE column1 = column2");
  }

  @Test
  public void testWithColumn_SingleCondition() {
    final String sql = select("column42").from("table").where(comparedField(column("column1")).isEqualTo(column("column2"))).toSql();
    assertThat(sql).isEqualTo("SELECT column42 FROM table WHERE column1 = column2");
  }

  @Test
  public void testSelectWithColumns_SingleCondition() {
    final String sql = select("column42", "column49").from("table")
      .where(comparedField(column("column1")).isEqualTo(column("column2"))).toSql();

    assertThat(sql).isEqualTo("SELECT column42,column49 FROM table WHERE column1 = column2");
  }

  @Test
  public void testSimpleSelect_MultipleConditions() {
    final String sql = select().from("table")
      .where(
        comparedField(column("column1")).isEqualTo(column("column2"))
          .and(comparedField(column("column4")).isEqualTo(column("column5")))
      ).toSql();

    assertThat(sql).isEqualTo("SELECT * FROM table WHERE (column1 = column2 AND column4 = column5)");
  }

  @Test
  public void testWithColumn_MultipleConditions() {
    final String sql = select("column42").from("table")
      .where(
        comparedField(column("column1")).isEqualTo(column("column2"))
          .and(comparedField(column("column4")).isEqualTo(column("column5")))
      ).toSql();

    assertThat(sql).isEqualTo("SELECT column42 FROM table WHERE (column1 = column2 AND column4 = column5)");
  }

  @Test
  public void testSelectWithColumns_MultipleConditions() {
    final String sql = select("column42", "column49").from("table")
      .where(
        comparedField(column("column1")).isEqualTo(column("column2"))
          .and(comparedField(column("column4")).isEqualTo(column("column5")))
      ).toSql();

    assertThat(sql).isEqualTo("SELECT column42,column49 FROM table WHERE (column1 = column2 AND column4 = column5)");
  }

  @Test
  public void testSimpleSelect_Between() {
    final String sql = select().from("table")
      .where(comparedField(column("x")).isBetween(column("from"), column("to")))
      .toSql();

    assertThat(sql).isEqualTo("SELECT * FROM table WHERE x BETWEEN from AND to");
  }

  @Test
  public void testSimpleSelect_BetweenOrSomethingElse() {
    final String sql = select().from("table")
      .where(
        comparedField(column("x")).isBetween(column("from"), column("to"))
          .or(comparedField(column("y")).isNull())
      )
      .toSql();

    assertThat(sql).isEqualTo("SELECT * FROM table WHERE (x BETWEEN from AND to OR y IS NULL)");
  }

  @Test
  public void testSelect_lessOrEqualThanCondition(){
    final String sql = select()
            .from("table")
            .where(comparedField(column("column1")).isLessOrEqualThan(column("column2")))
            .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE column1 <= column2");
  }

  @Test
  public void testSelect_greaterOrEqualThanCondition(){
    final String sql = select()
            .from("table")
            .where(comparedField(column("column1")).isGreaterOrEqualThan(column("column2")))
            .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE column1 >= column2");
  }

  @Test
  public void testSelect_inConditionLiterals(){
    final String sql = select()
            .from("table")
            .where(comparedField(column("column1"))
                .in(value("val1"), value("val2"), value("val3")))
            .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE column1 in ( ?, ?, ? )");
  }

  @Test
  public void testSelect_inConditionColumn(){
    final String sql = select()
        .from("table")
        .where(comparedField(column("column1")).in(column("column2")))
        .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE column1 in ( column2 )");
  }

  @Test
  public void testSelect_likeConditionLiterals(){
    final String sql = select()
        .from("table")
        .where(comparedField(column("column1")).like(value("%some%pattern__%")))
        .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE column1 like ?");
  }

  @Test
  public void testSelect_likeConditionColumn(){
    final String sql = select()
        .from("table")
        .where(comparedField(column("column1")).like(column("column2")))
        .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE column1 like column2");
  }

  @Test
  public void testSelect_notLikeConditionLiterals(){
    final String sql = select()
        .from("table")
        .where(comparedField(column("column1")).notLike(value("%some%pattern__%")))
        .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE column1 not like ?");
  }

  @Test
  public void testSelect_notLikeConditionColumn(){
    final String sql = select()
        .from("table")
        .where(comparedField(column("column1")).notLike(column("column2")))
        .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE column1 not like column2");
  }


  @Test
  public void testAllVariants() {
    final String sql = select().from("table")
      .where(
        comparedField(column("y1")).isNull()
          .and(comparedField(column("x")).isBetween(column("from"), column("to")))
          .or(comparedField(column("y")).isNull())
          .or(
            comparedField(column("a")).isNotNull()
              .and(comparedField(column("b")).isLessThan(column("x")))
              .and(comparedField(column("c")).isGreaterThan(column("x"))
              )
          )).toSql();

    assertThat(sql).isEqualTo("SELECT * FROM table WHERE " +
      "(((y1 IS NULL AND x BETWEEN from AND to) OR y IS NULL) OR ((a IS NOT NULL AND b < x) AND c > x))");
  }


  @Test
  public void testOrderBy() {
    final String sql = select().from("table").orderBy(column("x")).toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table ORDER BY x");
  }

  @Test
  public void testOrderBy_WithConditions() {
    final String sql = select().from("table")
      .where(comparedField(column("x")).isEqualTo(column("y")))
      .orderBy(column("x"))
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE x = y ORDER BY x");
  }

  @Test
  public void testSqlGenerationInFor() {
    Condition conditionRoot = comparedField(column("xxx")).isEqualTo(column("yyy"));

    for (int i = 0; i < 10; i++) {
      conditionRoot = conditionRoot.and(comparedField(column(String.valueOf(i)))
        .isEqualTo(column(String.valueOf(i + 1))));
    }

    final String sql = select().from("table")
      .where(conditionRoot)
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE ((((((((((xxx = yyy AND 0 = 1) AND 1 = 2) AND 2 = 3)" +
      " AND 3 = 4) AND 4 = 5) AND 5 = 6) AND 6 = 7) AND 7 = 8) AND 8 = 9) AND 9 = 10)");
  }

  @Test
  public void testSimpleSelectWithLiteralCondition() {
    final String sql = select().from("table")
      .where(comparedField(column("x")).isEqualTo(value(32)))
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE x = ?");
  }

  @Test
  public void testSimpleSelectWithLiteralBetween() {
    final String sql = select().from("table")
      .where(comparedField(column("x")).isBetween(value(32), value(43)))
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE x BETWEEN ? AND ?");
  }

  @Test
  public void testSimpleSelectWithLiteralBetween_LiteralAsSource() {
    final String sql = select().from("table")
      .where(comparedField(value("x")).isBetween(column("c"), value(43)))
      .toSql();
    assertThat(sql).isEqualTo("SELECT * FROM table WHERE ? BETWEEN c AND ?");
  }

  @Test
  public void testValueBindingWithLiteralBetween() {
    final ValueConstructor values = select().from("table")
      .where(comparedField(column("x")).isBetween(value(32), value(43)))
      .toValues();

    assertThat(values.toArray()).isEqualTo(new Object[]{32, 43});
  }

  @Test
  public void testValueBindingWithLiteralBetween_LiteralAsSource() {
    final ValueConstructor values = select().from("table")
      .where(comparedField(value("x")).isBetween(column("c"), value(43)))
      .toValues();

    assertThat(values.toArray()).isEqualTo(new Object[]{"x", 43});
  }

  @Test
  public void testValueBindingWithLiteralBetween_LiteralAsSourceMultipletimes() {
    final Select select = select();
    final ValueConstructor values = select.from("table")
      .where(
        comparedField(value("x")).isBetween(column("c"), value(43))
          .and(comparedField(column("y")).isEqualTo(value(28)))
          .and(
            comparedField(column("z")).isEqualTo(value(32))
              .or(comparedField(column("w")).isGreaterThan(value(50)))
          )
          .or(comparedField(column("a")).isNotEqualTo(value(55)))
      )
      .toValues();

    assertThat(values.toArray()).isEqualTo(new Object[]{"x", 43, 28, 32, 50, 55});
    assertThat(select.toSql())
      .isEqualTo("SELECT * FROM table WHERE (((? BETWEEN c AND ? AND y = ?) AND (z = ? OR w > ?)) OR a <> ?)");
  }
}
