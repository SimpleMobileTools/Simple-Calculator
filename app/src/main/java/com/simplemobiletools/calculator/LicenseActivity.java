package com.simplemobiletools.calculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LicenseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.license_butterknife_title)
    public void butterKnifeClicked() {
        openUrl(R.string.butterknife_url);
    }

    @OnClick(R.id.license_ambilwarna_title)
    public void ambilwarnaClicked() {
        openUrl(R.string.ambilwarna_url);
    }

    @OnClick(R.id.license_autofittextview_title)
    public void autofittextviewClicked() {
        openUrl(R.string.autofittextview_url);
    }

    @OnClick(R.id.license_robolectric_title)
    public void robolectricClicked() {
        openUrl(R.string.robolectric_url);
    }

    @OnClick(R.id.license_espresso_title)
    public void espressoClicked() {
        openUrl(R.string.espresso_url);
    }

    private void openUrl(int id) {
        final String url = getResources().getString(id);
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
