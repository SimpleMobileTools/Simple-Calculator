package com.simplemobiletools.calculator;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.RemoteViews;

import com.simplemobiletools.calculator.activities.MainActivity;

public class MyWidgetProvider extends AppWidgetProvider implements Calculator {
    private static RemoteViews mRemoteViews;
    private static CalculatorImpl mCalc;
    private static AppWidgetManager mWidgetManager;
    private static Intent mIntent;
    private static Context mContext;
    private static SharedPreferences mPrefs;

    private static int[] mWidgetIds;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        initVariables(context);

        mIntent = new Intent(context, MyWidgetProvider.class);
        setupIntent(Constants.DECIMAL, R.id.btn_decimal);
        setupIntent(Constants.ZERO, R.id.btn_0);
        setupIntent(Constants.ONE, R.id.btn_1);
        setupIntent(Constants.TWO, R.id.btn_2);
        setupIntent(Constants.THREE, R.id.btn_3);
        setupIntent(Constants.FOUR, R.id.btn_4);
        setupIntent(Constants.FIVE, R.id.btn_5);
        setupIntent(Constants.SIX, R.id.btn_6);
        setupIntent(Constants.SEVEN, R.id.btn_7);
        setupIntent(Constants.EIGHT, R.id.btn_8);
        setupIntent(Constants.NINE, R.id.btn_9);

        setupIntent(Constants.EQUALS, R.id.btn_equals);
        setupIntent(Constants.PLUS, R.id.btn_plus);
        setupIntent(Constants.MINUS, R.id.btn_minus);
        setupIntent(Constants.MULTIPLY, R.id.btn_multiply);
        setupIntent(Constants.DIVIDE, R.id.btn_divide);
        setupIntent(Constants.MODULO, R.id.btn_modulo);
        setupIntent(Constants.POWER, R.id.btn_power);
        setupIntent(Constants.ROOT, R.id.btn_root);
        setupIntent(Constants.CLEAR, R.id.btn_clear);
        setupIntent(Constants.RESET, R.id.btn_reset);

        setupAppOpenIntent(R.id.formula);
        setupAppOpenIntent(R.id.result);

        updateWidget();
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void setupIntent(String action, int id) {
        mIntent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);
        mRemoteViews.setOnClickPendingIntent(id, pendingIntent);
    }

    private void setupAppOpenIntent(int id) {
        final Intent intent = new Intent(mContext, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        mRemoteViews.setOnClickPendingIntent(id, pendingIntent);
    }

    private void initVariables(Context context) {
        mContext = context;
        updateWidgetIds();
        mPrefs = initPrefs(mContext);
        final int defaultColor = mContext.getResources().getColor(R.color.text_grey);
        final int newBgColor = mPrefs.getInt(Constants.WIDGET_BG_COLOR, defaultColor);
        final int newTextColor = mPrefs.getInt(Constants.WIDGET_TEXT_COLOR, Color.WHITE);

        mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.activity_main);
        mRemoteViews.setViewVisibility(R.id.btn_reset, View.VISIBLE);
        mRemoteViews.setInt(R.id.calculator_holder, "setBackgroundColor", newBgColor);

        updateTextColors(newTextColor);
        mWidgetManager = AppWidgetManager.getInstance(mContext);

        final String displayValue = "0";
        mCalc = new CalculatorImpl(this, displayValue);
    }

    private void updateWidgetIds() {
        final ComponentName component = new ComponentName(mContext, MyWidgetProvider.class);
        mWidgetManager = AppWidgetManager.getInstance(mContext);
        mWidgetIds = mWidgetManager.getAppWidgetIds(component);
    }

    private void updateWidget() {
        for (int widgetId : mWidgetIds) {
            mWidgetManager.updateAppWidget(widgetId, mRemoteViews);
        }
    }

    private SharedPreferences initPrefs(Context context) {
        return context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
    }

    private void updateTextColors(int color) {
        final int[] viewIds =
                new int[]{R.id.formula, R.id.result, R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6,
                        R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_modulo, R.id.btn_power, R.id.btn_root, R.id.btn_clear, R.id.btn_reset,
                        R.id.btn_divide, R.id.btn_multiply, R.id.btn_minus, R.id.btn_plus, R.id.btn_decimal, R.id.btn_equals};

        for (int i : viewIds) {
            mRemoteViews.setInt(i, "setTextColor", color);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        switch (action) {
            case Constants.DECIMAL:
            case Constants.ZERO:
            case Constants.ONE:
            case Constants.TWO:
            case Constants.THREE:
            case Constants.FOUR:
            case Constants.FIVE:
            case Constants.SIX:
            case Constants.SEVEN:
            case Constants.EIGHT:
            case Constants.NINE:
            case Constants.EQUALS:
            case Constants.CLEAR:
            case Constants.RESET:
            case Constants.PLUS:
            case Constants.MINUS:
            case Constants.MULTIPLY:
            case Constants.DIVIDE:
            case Constants.MODULO:
            case Constants.POWER:
            case Constants.ROOT:
                myAction(action, context);
                break;
            default:
                super.onReceive(context, intent);
        }
    }

    private void myAction(String action, Context context) {
        if (mCalc == null || mRemoteViews == null || mWidgetManager == null || mPrefs == null || mContext == null) {
            initVariables(context);
        }

        switch (action) {
            case Constants.DECIMAL:
                mCalc.numpadClicked(R.id.btn_decimal);
                break;
            case Constants.ZERO:
                mCalc.numpadClicked(R.id.btn_0);
                break;
            case Constants.ONE:
                mCalc.numpadClicked(R.id.btn_1);
                break;
            case Constants.TWO:
                mCalc.numpadClicked(R.id.btn_2);
                break;
            case Constants.THREE:
                mCalc.numpadClicked(R.id.btn_3);
                break;
            case Constants.FOUR:
                mCalc.numpadClicked(R.id.btn_4);
                break;
            case Constants.FIVE:
                mCalc.numpadClicked(R.id.btn_5);
                break;
            case Constants.SIX:
                mCalc.numpadClicked(R.id.btn_6);
                break;
            case Constants.SEVEN:
                mCalc.numpadClicked(R.id.btn_7);
                break;
            case Constants.EIGHT:
                mCalc.numpadClicked(R.id.btn_8);
                break;
            case Constants.NINE:
                mCalc.numpadClicked(R.id.btn_9);
                break;
            case Constants.EQUALS:
                mCalc.handleEquals();
                break;
            case Constants.CLEAR:
                mCalc.handleClear();
                break;
            case Constants.RESET:
                mCalc.handleReset();
                break;
            case Constants.PLUS:
            case Constants.MINUS:
            case Constants.MULTIPLY:
            case Constants.DIVIDE:
            case Constants.MODULO:
            case Constants.POWER:
            case Constants.ROOT:
                mCalc.handleOperation(action);
                break;
            default:
                break;
        }
    }

    @Override
    public void setValue(String value) {
        mRemoteViews.setTextViewText(R.id.result, value);
        updateWidget();
    }

    @Override
    public void setValueDouble(double d) {

    }

    public void setFormula(String value) {
        mRemoteViews.setTextViewText(R.id.formula, value);
        updateWidget();
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        if (mContext != null)
            updateWidgetIds();
    }
}
