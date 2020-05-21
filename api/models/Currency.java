package excercises.programs.currencyConverter.api.models;

public class Currency {
    final private String symbol;
    final private String name;

    public Currency ( String symbol, String name )
    {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.symbol + ": " + this.name;
    }
}
