package com.simplemobiletools.calculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import me.grantland.widget.AutofitHelper;

public class MainActivity extends AppCompatActivity implements Calculator {
    @BindView(R.id.result) TextView result;
    @BindView(R.id.formula) TextView formula;

    private CalculatorImpl calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        calc = new CalculatorImpl(this);
        setupResultView();
        AutofitHelper.create(result);
        AutofitHelper.create(formula);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                final Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupResultView() {
        final Resources res = getResources();
        result.setBackgroundColor(res.getColor(android.R.color.white));
        result.setTextColor(res.getColor(R.color.text_grey));

        formula.setBackgroundColor(res.getColor(android.R.color.white));
        formula.setTextColor(res.getColor(R.color.text_grey));
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

    @OnLongClick(R.id.formula)
    public boolean formulaLongPressed() {
        copyToClipboard(false);
        return true;
    }

    @OnLongClick(R.id.result)
    public boolean resultLongPressed() {
        copyToClipboard(true);
        return true;
    }

    private void copyToClipboard(boolean copyResult) {
        String value;
        if (copyResult) {
            value = result.getText().toString();
        } else {
            value = formula.getText().toString();
        }

        final ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        final ClipData clip = ClipData.newPlainText(getResources().getString(R.string.app_name), value);
        clipboard.setPrimaryClip(clip);
        Utils.showToast(getApplicationContext(), R.string.copied_to_clipboard);
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

    @Override
    public void setFormula(String value) {
        formula.setText(value);
    }

    public CalculatorImpl getCalc() {
        return calc;
    }
}
