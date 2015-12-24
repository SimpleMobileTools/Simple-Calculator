package calculator.simplemobiletools.com.simple_calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.result) TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void addDigit(int number) {
        final String currentValue = result.getText().toString();
        final String newValue = getFormattedValue(currentValue + number);
        result.setText(newValue);
    }

    private String getFormattedValue(String str) {
        return formatDouble(Double.parseDouble(str));
    }

    private String formatDouble(double d) {
        if (d == (long) d) {
            return String.format("%d", (long) d);
        } else {
            return String.format("%s", d);
        }
    }

    @OnClick({R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9})
    public void digitClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_0:
                addDigit(0);
                break;
            case R.id.btn_1:
                addDigit(1);
                break;
            case R.id.btn_2:
                addDigit(2);
                break;
            case R.id.btn_3:
                addDigit(3);
                break;
            case R.id.btn_4:
                addDigit(4);
                break;
            case R.id.btn_5:
                addDigit(5);
                break;
            case R.id.btn_6:
                addDigit(6);
                break;
            case R.id.btn_7:
                addDigit(7);
                break;
            case R.id.btn_8:
                addDigit(8);
                break;
            case R.id.btn_9:
                addDigit(9);
                break;
            default:
                break;
        }
    }
}
