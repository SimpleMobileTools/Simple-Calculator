package calculator.simplemobiletools.com.simple_calculator;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider implements Calculator {
    private static RemoteViews remoteViews;
    private static CalculatorImpl calc;
    private static AppWidgetManager widgetManager;
    private static int[] widgetIds;
    private static Intent intent;
    private static Context cxt;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        widgetManager = appWidgetManager;
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_main);
        calc = new CalculatorImpl(this);
        widgetIds = appWidgetIds;
        cxt = context;

        intent = new Intent(context, MyWidgetProvider.class);
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

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    private void setupIntent(String action, int id) {
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(cxt, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(id, pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case Constants.DECIMAL:
                calc.numpadClicked(R.id.btn_decimal);
                break;
            case Constants.ZERO:
                calc.numpadClicked(R.id.btn_0);
                break;
            case Constants.ONE:
                calc.numpadClicked(R.id.btn_1);
                break;
            case Constants.TWO:
                calc.numpadClicked(R.id.btn_2);
                break;
            case Constants.THREE:
                calc.numpadClicked(R.id.btn_3);
                break;
            case Constants.FOUR:
                calc.numpadClicked(R.id.btn_4);
                break;
            case Constants.FIVE:
                calc.numpadClicked(R.id.btn_5);
                break;
            case Constants.SIX:
                calc.numpadClicked(R.id.btn_6);
                break;
            case Constants.SEVEN:
                calc.numpadClicked(R.id.btn_7);
                break;
            case Constants.EIGHT:
                calc.numpadClicked(R.id.btn_8);
                break;
            case Constants.NINE:
                calc.numpadClicked(R.id.btn_9);
                break;
            case Constants.EQUALS:
                calc.handleEquals();
                break;
            case Constants.CLEAR:
                calc.handleClear();
                break;
            case Constants.PLUS:
            case Constants.MINUS:
            case Constants.MULTIPLY:
            case Constants.DIVIDE:
            case Constants.MODULO:
            case Constants.POWER:
            case Constants.ROOT:
                calc.handleOperation(action);
                break;
            default:
                super.onReceive(context, intent);
        }
    }

    @Override
    public void setValue(String value) {
        remoteViews.setTextViewText(R.id.result, value);
        widgetManager.updateAppWidget(widgetIds, remoteViews);
    }

    @Override
    public void setValueDouble(double d) {

    }
}
