package org.nevack.android.resistorbee;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private ResistorView resistorView;
    private TextView resistorInfo;
    private Spinner firstNumber;
    private Spinner secondNumber;
    private Spinner thirdNumber;
    private Spinner multiplier;
    private Spinner tolerance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences saved = getPreferences(0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resistorView = (ResistorView) findViewById(R.id.resistor);
        resistorInfo = (TextView) findViewById(R.id.info);

        firstNumber = (Spinner) findViewById(R.id.firstNumber);
        secondNumber = (Spinner) findViewById(R.id.secondNumber);
        thirdNumber = (Spinner) findViewById(R.id.thirdNumber);
        multiplier = (Spinner) findViewById(R.id.multiplier);
        tolerance = (Spinner) findViewById(R.id.tolerance);

        firstNumber.setAdapter(new NumberAdapter(this));
        secondNumber.setAdapter(new NumberAdapter(this));
        thirdNumber.setAdapter(new NumberAdapter(this));
        multiplier.setAdapter(new MultiplierAdapter(this));
        tolerance.setAdapter(new ToleranceAdapter(this));

        firstNumber.setOnItemSelectedListener(new Listener(0));
        secondNumber.setOnItemSelectedListener(new Listener(1));
        thirdNumber.setOnItemSelectedListener(new Listener(2));
        multiplier.setOnItemSelectedListener(new Listener(3));
        tolerance.setOnItemSelectedListener(new Listener(4));

        firstNumber.setSelection(saved.getInt("first", 0));
        secondNumber.setSelection(saved.getInt("second", 0));
        thirdNumber.setSelection(saved.getInt("third", 0));
        multiplier.setSelection(saved.getInt("multiplier", 0));
        tolerance.setSelection(saved.getInt("tolerance", 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.swap:
                int first = firstNumber.getSelectedItemPosition();
                int second = secondNumber.getSelectedItemPosition();
                int forth = multiplier.getSelectedItemPosition();
                int fifth = tolerance.getSelectedItemPosition();

                firstNumber.setSelection(fifth);
                secondNumber.setSelection(forth);
                multiplier.setSelection(second);
                tolerance.setSelection(first);
                
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences saved = getPreferences(0);
        SharedPreferences.Editor editor = saved.edit();

        editor.putInt("first", firstNumber.getSelectedItemPosition());
        editor.putInt("second", secondNumber.getSelectedItemPosition());
        editor.putInt("third", thirdNumber.getSelectedItemPosition());
        editor.putInt("multiplier", multiplier.getSelectedItemPosition());
        editor.putInt("tolerance", tolerance.getSelectedItemPosition());

        editor.commit();
    }

    class Listener implements AdapterView.OnItemSelectedListener {

        private final DecimalFormat format = new DecimalFormat("0.##");

        private int position;

        public Listener(int position) {
            this.position = position;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            resistorView.setStrokeColor(this.position, ((ColorCode) parent.getAdapter().getItem(position)).getColor());
            int first = (int) ((ColorCode) firstNumber.getSelectedItem()).getValue();
            int second = (int) ((ColorCode) secondNumber.getSelectedItem()).getValue();
            int third = (int) ((ColorCode) thirdNumber.getSelectedItem()).getValue();
            float multi = ((ColorCode) multiplier.getSelectedItem()).getValue();
            float tol = ((ColorCode) tolerance.getSelectedItem()).getValue();
            float sum = (first * 100 + second * 10 + third) * multi;
            String prefix = " ";
            if (sum > 1000000) {
                sum /= 1000000;
                prefix = " M";
            } else if (sum > 1000) {
                sum /= 1000;
                prefix = " k";
            } else if (sum < 1 && sum > 0) {
                sum *= 1000;
                prefix = " m";
            }
            resistorInfo.setText(format.format(sum) + prefix + "Ω ±" + format.format(tol) + "%");
        }

        @Override public void onNothingSelected(AdapterView<?> parent) {}
    }
}
