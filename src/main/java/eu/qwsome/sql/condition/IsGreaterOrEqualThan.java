package eu.qwsome.sql.condition;

public class IsGreaterOrEqualThan extends BiCondition {
    IsGreaterOrEqualThan(ValueHolder first, ValueHolder second) {
        super(first, second);
    }

    @Override
    String getOperator() {
        return " >= ";
    }
}
