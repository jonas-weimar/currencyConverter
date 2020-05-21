package excercises.programs.currencyConverter.api.models;

import excercises.programs.currencyConverter.api.models.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyList {
    private List<Currency> currencies;

    public CurrencyList()
    {
        this.currencies = new ArrayList<Currency>();
    }

    public void add( Currency c ){
        this.currencies.add(c);
    }

    public void setCurrencies( List<Currency> currencies ){
        this.currencies = currencies;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }
}
