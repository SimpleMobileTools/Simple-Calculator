package com.simplemobiletools.calculator.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.simplemobiletools.calculator.Calculator;
import com.simplemobiletools.calculator.CalculatorImpl;
import com.simplemobiletools.calculator.Config;
import com.simplemobiletools.calculator.Constants;
import com.simplemobiletools.calculator.Formatter;
import com.simplemobiletools.calculator.R;
import com.simplemobiletools.calculator.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import me.grantland.widget.AutofitHelper;

public class MainActivity extends AppCompatActivity implements Calculator {
    @BindView(R.id.result) TextView mResult;
    @BindView(R.id.formula) TextView mFormula;

    private static CalculatorImpl mCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mCalc = new CalculatorImpl(this);
        setupResultView();
        AutofitHelper.create(mResult);
        AutofitHelper.create(mFormula);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Config.newInstance(getApplicationContext()).setIsFirstRun(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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
        mResult.setBackgroundColor(res.getColor(android.R.color.white));
        mResult.setTextColor(res.getColor(R.color.text_grey));

        mFormula.setBackgroundColor(res.getColor(android.R.color.white));
        mFormula.setTextColor(res.getColor(R.color.text_grey));
    }

    @OnClick(R.id.btn_plus)
    public void plusClicked() {
        mCalc.handleOperation(Constants.PLUS);
    }

    @OnClick(R.id.btn_minus)
    public void minusClicked() {
        mCalc.handleOperation(Constants.MINUS);
    }

    @OnClick(R.id.btn_multiply)
    public void multiplyClicked() {
        mCalc.handleOperation(Constants.MULTIPLY);
    }

    @OnClick(R.id.btn_divide)
    public void divideClicked() {
        mCalc.handleOperation(Constants.DIVIDE);
    }

    @OnClick(R.id.btn_modulo)
    public void moduloClicked() {
        mCalc.handleOperation(Constants.MODULO);
    }

    @OnClick(R.id.btn_power)
    public void powerClicked() {
        mCalc.handleOperation(Constants.POWER);
    }

    @OnClick(R.id.btn_root)
    public void rootClicked() {
        mCalc.handleOperation(Constants.ROOT);
    }

    @OnClick(R.id.btn_clear)
    public void clearClicked() {
        mCalc.handleClear();
    }

    @OnLongClick(R.id.btn_clear)
    public boolean clearLongClicked() {
        mCalc.handleReset();
        return true;
    }

    @OnClick(R.id.btn_equals)
    public void equalsClicked() {
        mCalc.handleEquals();
    }

    @OnClick({R.id.btn_decimal, R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8,
            R.id.btn_9})
    public void numpadClick(View view) {
        numpadClicked(view.getId());
    }

    public void numpadClicked(int id) {
        mCalc.numpadClicked(id);
    }

    @OnLongClick(R.id.formula)
    public boolean formulaLongPressed() {
        return copyToClipboard(false);
    }

    @OnLongClick(R.id.result)
    public boolean resultLongPressed() {
        return copyToClipboard(true);
    }

    private boolean copyToClipboard(boolean copyResult) {
        String value = mFormula.getText().toString().trim();
        if (copyResult) {
            value = mResult.getText().toString().trim();
        }

        if (value.isEmpty())
            return false;

        final ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        final ClipData clip = ClipData.newPlainText(getResources().getString(R.string.app_name), value);
        clipboard.setPrimaryClip(clip);
        Utils.showToast(getApplicationContext(), R.string.copied_to_clipboard);
        return true;
    }

    @Override
    public void setValue(String value) {
        mResult.setText(value);
    }

    // used only by Robolectric
    @Override
    public void setValueDouble(double d) {
        mCalc.setValue(Formatter.doubleToString(d));
        mCalc.setLastKey(Constants.DIGIT);
    }

    public void setFormula(String value) {
        mFormula.setText(value);
    }

    public CalculatorImpl getCalc() {
        return mCalc;
    }
}
