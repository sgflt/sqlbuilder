package eu.qwsome.sql.condition;

public class IsLessOrEqualThan extends BiCondition {

    IsLessOrEqualThan(ValueHolder first, ValueHolder second) {
        super(first, second);
    }

    @Override
    String getOperator() {
        return " <= ";
    }
}
