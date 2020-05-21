package excercises.programs.currencyConverter;

import excercises.programs.currencyConverter.api.models.CurrencyList;
import excercises.programs.currencyConverter.api.models.Currency;
import excercises.programs.currencyConverter.api.CurrencyApi;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;


/**
 * Main Frame of the currency calculator application
 *
 * @uses api and models described in the api package
 */
public class FrmMain extends JFrame {
    private CurrencyList currencies = new CurrencyList();
    private JPanel rootPanel = new JPanel();
    private JPanel baseCurrencyPanel = new JPanel();
    private JPanel toCurrencyPanel = new JPanel();
    private JComboBox<Currency> baseCBX = new JComboBox<Currency>();
    private JComboBox<Currency> toCBX = new JComboBox<Currency>();
    private JSlider baseSlider = new JSlider(JSlider.HORIZONTAL);
    private JLabel baseCurrencyValueLabel = new JLabel();
    private JLabel toCurrencyValueLabel = new JLabel();
    private JButton calculationButton = new JButton( "Calc" );

    public FrmMain ()
    {
        this.currencies.add(
                new Currency("EUR", "Euro")
        );
        this.currencies.add(
                new Currency("USD", "US Dollar")
        );
        this.currencies.add(
                new Currency("GBP", "Britain Pound")
        );
        this.currencies.add(
                new Currency("CAD", "Canadian Dollar")
        );
        this.currencies.add(
                new Currency("JPY", "Japanese Yen")
        );
        this.currencies.add(
                new Currency("CHF", "Swiss Franc")
        );
        this.currencies.add(
                new Currency("CZK", "Czech Koruna")
        );
        this.currencies.add(
                new Currency("HUF", "Hungarian Forint")
        );
        this.currencies.add(
                new Currency("TRY", "Turkish Lira")
        );
    }


    private void setup ()
    {
        // set layouts for the root, base and to panels
        // root is border rest is flow
        rootPanel.setLayout( new BorderLayout() );
        baseCurrencyPanel.setLayout( new FlowLayout() );
        toCurrencyPanel.setLayout( new FlowLayout() );


        // iterate over currencies list and add items
        // to the checkboxes
        for ( Currency item : currencies.getCurrencies() )
        {
            baseCBX.addItem( item );
            toCBX.addItem( item );
        }


        // add eventlisteners for calculation button and
        // base currency slider
        baseSlider.addChangeListener( new BaseCurrencySliderEventListener() );
        calculationButton.addActionListener( new CalculationButtonEventListener() );


        // set labels
        baseCurrencyValueLabel.setText( String.valueOf( baseSlider.getValue() ) );
        toCurrencyValueLabel.setText( "Please press 'Calc'." );


        // add basecurrencypanel items to its
        // parent panel
        baseCurrencyPanel.add( baseSlider );
        baseCurrencyPanel.add( baseCurrencyValueLabel );
        baseCurrencyPanel.add( baseCBX );


        // add tocurrencypanel items to its
        // parent panel
        toCurrencyPanel.add( toCurrencyValueLabel );
        toCurrencyPanel.add( toCBX );
        toCurrencyPanel.add( calculationButton );


        // add both parents to the root panel
        // choosing north and center layout
        rootPanel.add( baseCurrencyPanel, BorderLayout.NORTH );
        rootPanel.add( toCurrencyPanel, BorderLayout.CENTER );


        // add root panel to FrmMain
        this.add( rootPanel );
    }


    private class BaseCurrencySliderEventListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {

            // change the baseCurrencyLabel text on Slider change
            baseCurrencyValueLabel.setText(
                    String.valueOf( baseSlider.getValue() )
            );
        }
    }


    private class CalculationButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            // get selected currency items
            Currency baseCurrency = (Currency) baseCBX.getSelectedItem();
            Currency toCurrency = (Currency) toCBX.getSelectedItem();


            // try / catch becaus of IOException
            try {

                // make request to the api and store response
                HashMap<String, Double> res = new CurrencyApi()
                        .makeRequest(
                                baseCurrency.getSymbol(),
                                toCurrency.getSymbol()
                        );

                // calculate response * set value and set value
                // to the toCurrencyValueLabel via .setText()
                double rawResponse = res.get( toCurrency.getSymbol() ) *
                        Integer.parseInt( baseCurrencyValueLabel.getText() );

                toCurrencyValueLabel.setText( String.valueOf( rawResponse ) );

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void load()
    {
        this.setSize(500, 100);
        this.setup();
        this.setTitle("Currency Converter");
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
        this.setVisible( true );
    }


    public static void main(String[] args) {
        FrmMain frm = new FrmMain();

        frm.load();
    }
}
