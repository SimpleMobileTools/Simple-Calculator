package com.simplemobiletools.calculator.activities;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import com.simplemobiletools.calculator.Constants;
import com.simplemobiletools.calculator.MyWidgetProvider;
import com.simplemobiletools.calculator.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yuku.ambilwarna.AmbilWarnaDialog;

public class WidgetConfigureActivity extends AppCompatActivity {
    @BindView(R.id.btn_reset) View mResetBtn;
    @BindView(R.id.config_bg_color) View mBgColorPicker;
    @BindView(R.id.config_bg_seekbar) SeekBar mBgSeekBar;
    @BindView(R.id.config_text_color) View mTextColorPicker;
    @BindView(R.id.config_calc) View mBackground;
    @BindView(R.id.config_save) Button mSaveBtn;
    @BindView(R.id.result) TextView mResult;
    @BindView(R.id.formula) TextView mFormula;

    private int mBgColor;
    private int mBgColorWithoutTransparency;
    private int mWidgetId;
    private int mTextColor;
    private float mBgAlpha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.widget_config);
        ButterKnife.bind(this);
        initVariables();

        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        if (extras != null)
            mWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        if (mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
            finish();
    }

    private void initVariables() {
        final SharedPreferences prefs = getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE);
        mBgColor = prefs.getInt(Constants.WIDGET_BG_COLOR, 1);
        if (mBgColor == 1) {
            mBgColor = Color.BLACK;
            mBgAlpha = .2f;
        } else {
            mBgAlpha = Color.alpha(mBgColor) / (float) 255;
        }

        mResetBtn.setVisibility(View.VISIBLE);
        mBgColorWithoutTransparency = Color.rgb(Color.red(mBgColor), Color.green(mBgColor), Color.blue(mBgColor));
        mBgSeekBar.setOnSeekBarChangeListener(bgSeekbarChangeListener);
        mBgSeekBar.setProgress((int) (mBgAlpha * 100));
        updateBackgroundColor();

        mTextColor = prefs.getInt(Constants.WIDGET_TEXT_COLOR, getResources().getColor(R.color.colorPrimary));
        updateTextColor();

        mFormula.setText("15,937*5");
        mResult.setText("79,685");
    }

    @OnClick(R.id.config_save)
    public void saveConfig() {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        final RemoteViews views = new RemoteViews(getPackageName(), R.layout.activity_main);
        views.setInt(R.id.calculator_holder, "setBackgroundColor", mBgColor);
        appWidgetManager.updateAppWidget(mWidgetId, views);

        storeWidgetBackground();
        requestWidgetUpdate();

        final Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    private void storeWidgetBackground() {
        final SharedPreferences prefs = getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE);
        prefs.edit().putInt(Constants.WIDGET_BG_COLOR, mBgColor).apply();
        prefs.edit().putInt(Constants.WIDGET_TEXT_COLOR, mTextColor).apply();
    }

    private void requestWidgetUpdate() {
        final Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, MyWidgetProvider.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{mWidgetId});
        sendBroadcast(intent);
    }

    private void updateBackgroundColor() {
        mBgColor = adjustAlpha(mBgColorWithoutTransparency, mBgAlpha);
        mBackground.setBackgroundColor(mBgColor);
        mBgColorPicker.setBackgroundColor(mBgColor);
        mSaveBtn.setBackgroundColor(mBgColor);
    }

    private void updateTextColor() {
        mTextColorPicker.setBackgroundColor(mTextColor);
        mSaveBtn.setTextColor(mTextColor);

        int[] viewIds =
                new int[]{R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8,
                        R.id.btn_9, R.id.btn_modulo, R.id.btn_power, R.id.btn_root, R.id.btn_clear, R.id.btn_reset, R.id.btn_divide,
                        R.id.btn_multiply, R.id.btn_minus, R.id.btn_plus, R.id.btn_decimal, R.id.btn_equals};
        mResult.setTextColor(mTextColor);
        mFormula.setTextColor(mTextColor);

        Button btn;
        for (int i : viewIds) {
            btn = (Button) findViewById(i);
            btn.setTextColor(mTextColor);
        }
    }

    @OnClick(R.id.config_bg_color)
    public void pickBackgroundColor() {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, mBgColorWithoutTransparency, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mBgColorWithoutTransparency = color;
                updateBackgroundColor();
            }
        });

        dialog.show();
    }

    @OnClick(R.id.config_text_color)
    public void pickTextColor() {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, mTextColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mTextColor = color;
                updateTextColor();
            }
        });

        dialog.show();
    }

    private SeekBar.OnSeekBarChangeListener bgSeekbarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mBgAlpha = (float) progress / (float) 100;
            updateBackgroundColor();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private int adjustAlpha(int color, float factor) {
        final int alpha = Math.round(Color.alpha(color) * factor);
        final int red = Color.red(color);
        final int green = Color.green(color);
        final int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
