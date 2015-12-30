package calculator.simplemobiletools.com.simple_calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity implements Calculator {
    @Bind(R.id.result) TextView result;

    private CalculatorImpl calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        calc = new CalculatorImpl(this);
    }

    @OnClick(R.id.btn_plus)
    public void plusClicked() {
        calc.handleOperation(Constants.PLUS);
    }

    @OnClick(R.id.btn_minus)
    public void minusClicked() {
        calc.handleOperation(Constants.MINUS);
    }

    @OnClick(R.id.btn_multiply)
    public void multiplyClicked() {
        calc.handleOperation(Constants.MULTIPLY);
    }

    @OnClick(R.id.btn_divide)
    public void divideClicked() {
        calc.handleOperation(Constants.DIVIDE);
    }

    @OnClick(R.id.btn_modulo)
    public void moduloClicked() {
        calc.handleOperation(Constants.MODULO);
    }

    @OnClick(R.id.btn_power)
    public void powerClicked() {
        calc.handleOperation(Constants.POWER);
    }

    @OnClick(R.id.btn_root)
    public void rootClicked() {
        calc.handleOperation(Constants.ROOT);
    }

    @OnClick(R.id.btn_clear)
    public void clearClicked() {
        calc.handleClear();
    }

    @OnLongClick(R.id.btn_clear)
    public boolean clearLongClicked() {
        calc.handleReset();
        return true;
    }

    @OnClick(R.id.btn_equals)
    public void equalsClicked() {
        calc.handleEquals();
    }

    @OnClick({R.id.btn_decimal, R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8,
            R.id.btn_9})
    public void numpadClick(View view) {
        numpadClicked(view.getId());
    }

    public void numpadClicked(int id) {
        calc.numpadClicked(id);
    }

    @Override
    public void setValue(String value) {
        result.setText(value);
    }

    // used only by Robolectric
    @Override
    public void setValueDouble(double d) {
        calc.setValue(Formatter.doubleToString(d));
        calc.setLastKey(Constants.DIGIT);
    }

    public CalculatorImpl getCalc() {
        return calc;
    }
}
