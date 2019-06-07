package eu.qwsome.sql.condition;

public class Not implements Condition {

    private final Condition condition;

    Not(final Condition condition){
        this.condition = condition;
    }

    @Override
    public ValueConstructor getValues() {
        return this.condition.getValues();
    }

    @Override
    public void appendTo(final StringBuilder builder) {
        builder.append("NOT ");
        this.condition.appendTo(builder);
    }
}
