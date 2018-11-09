package eu.qwsome.sql;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.util.Statistics;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static eu.qwsome.sql.Column.column;
import static eu.qwsome.sql.Select.select;
import static eu.qwsome.sql.ValueLiteral.value;
import static eu.qwsome.sql.condition.FieldComparator.comparedField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

/**
 * @implNote Tests may fail on different computers. At first measure performance on actual PC and
 * then you can refactor production code and compare results.
 */
public class SelectPerformanceTest {


  @Test
  public void benchmarkBasicSelect() throws RunnerException {
    final Options opt = new OptionsBuilder()
      .include(BasicSelectBenchmark.class.getSimpleName())
      .build();
    final Collection<RunResult> runResults = new Runner(opt).run();
    assertResultIsWithinRange(runResults, 1.128, 13.0, 2.0);
  }

  @Test
  public void benchmarkBasicSelectWithConditions() throws RunnerException {
    final Options opt = new OptionsBuilder()
      .include(BasicSelectWithConditions.class.getSimpleName())
      .build();
    final Collection<RunResult> runResults = new Runner(opt).run();
    assertResultIsWithinRange(runResults, 27.0, 32.0, 3.01);
  }

  @Test
  public void benchmarkBasicSelectWithJoin() throws RunnerException {
    final Options opt = new OptionsBuilder()
      .include(BasicSelectWithJoin.class.getSimpleName())
      .build();
    final Collection<RunResult> runResults = new Runner(opt).run();
    assertResultIsWithinRange(runResults, 34.0, 38.0, 4.0);
  }

  @Test
  public void benchmarkBasicSelectWithJoins() throws RunnerException {
    final Options opt = new OptionsBuilder()
      .include(BasicSelectWithMultipleJoins.class.getSimpleName())
      .build();
    final Collection<RunResult> runResults = new Runner(opt).run();
    assertResultIsWithinRange(runResults, 56.0, 64.0, 6.0);
  }

  @Test
  public void benchmarkComplexSelect() throws RunnerException {
    final Options opt = new OptionsBuilder()
      .include(ComplexSelect.class.getSimpleName())
      .build();
    final Collection<RunResult> runResults = new Runner(opt).run();
    assertResultIsWithinRange(runResults, 95.0, 106.0, 6.0);
  }

  private static void assertResultIsWithinRange(
    final Collection<RunResult> runResults,
    final double min,
    final double mean,
    final double offset
  ) {
    for(final RunResult runResult : runResults) {
      final Statistics statistics = runResult.getPrimaryResult().getStatistics();
      assertThat(statistics.getMean()).isCloseTo(mean, offset(offset));
      assertThat(statistics.getMin()).isGreaterThan(min);
    }
  }


  public static class BasicSelectBenchmark {
    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Measurement(batchSize = 100, time = 2)
    @Warmup(batchSize = 100, time = 2)
    public static String run() {
      return select().from("table").toSql();
    }
  }

  public static class BasicSelectWithConditions {
    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Measurement(batchSize = 100, time = 2)
    @Warmup(batchSize = 100, time = 2)
    public static String run() {
      return select().from("table")
        .where(
          comparedField(column("bazinga")).isBetween(value(646785), column("anohtercolumn"))
            .and(comparedField(value("djakdghaja")).isGreaterThan(column("cnvahjcmkcas")))
        ).toSql();
    }
  }

  public static class BasicSelectWithJoin {
    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Measurement(batchSize = 100, time = 2)
    @Warmup(batchSize = 100, time = 2)
    public static String run() {
      return select().from("table")
        .join("jointable")
        .on(
          comparedField(column("bazinga")).isBetween(value(646785), column("anohtercolumn"))
            .and(comparedField(value("djakdghaja")).isGreaterThan(column("cnvahjcmkcas")))
        ).toSql();
    }
  }


  public static class BasicSelectWithMultipleJoins {
    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Measurement(batchSize = 100, time = 2)
    @Warmup(batchSize = 100, time = 2)
    public static String run() {
      return select().from("table")
        .join("jointable")
        .on(
          comparedField(column("bazinga")).isBetween(value(646785), column("anohtercolumn"))
            .and(comparedField(value("djakdghaja")).isGreaterThan(column("cnvahjcmkcas")))
        )
        .join("jointable")
        .on(
          comparedField(column("bazinga")).isBetween(value(646785), column("anohtercolumn"))
            .and(comparedField(value("djakdghaja")).isGreaterThan(column("cnvahjcmkcas")))
        ).toSql();
    }
  }

  public static class ComplexSelect {
    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Measurement(batchSize = 100, time = 2)
    @Warmup(batchSize = 100, time = 2)
    public static String run() {
      return select("column1", "column2", "column3", "column4")
        .from("table")
        .join("jointable")
        .on(
          comparedField(column("bazinga")).isBetween(value(646785), column("anohtercolumn"))
            .and(comparedField(value("djakdghaja")).isGreaterThan(column("cnvahjcmkcas")))
        )
        .join("jointable")
        .on(
          comparedField(column("bazinga")).isBetween(value(646785), column("anohtercolumn"))
            .and(comparedField(value("djakdghaja")).isGreaterThan(column("cnvahjcmkcas")))
        )
        .where(
          comparedField(column("bazinga")).isBetween(value(646785), column("anohtercolumn"))
          .and(comparedField(value("djakdghaja")).isGreaterThan(column("cnvahjcmkcas")))
        )
        .orderBy(
          column("column1"),
          column("column2"),
          column("column3"),
          column("column4")
        ).toSql();
    }
  }
}