package eu.qwsome.sql.condition;

public class Like extends BiCondition {

    Like(ValueHolder first, ValueHolder second) {
        super(first, second);
    }

    @Override
    String getOperator() {
        return " like ";
    }
}
