package org.snowpard.proects.nineteen;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView txt_hello;
    private Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        loadLocale();

        TextView tvLanguage = (TextView) findViewById(R.id.tvLanguage);
        tvLanguage.setOnClickListener(viewClickListener);

        txt_hello = (TextView) findViewById(R.id.txt_hello);
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        updateTexts();
    }

    private void updateTexts() {
        txt_hello.setText(R.string.hello_world);
    }


    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (myLocale != null) {
            newConfig.locale = myLocale;
            Locale.setDefault(myLocale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    View.OnClickListener viewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }
    };

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.menu_language);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        changeLang("en");
                        return true;
                    case R.id.menu2:
                        changeLang("ru");
                        return true;
                    case R.id.menu3:
                        changeLang("uk");
                        return true;
                    default:
                        return false;
                }

            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

                Intent intent = getIntent();
                overridePendingTransition(0, 0);//4
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//5
                finish();//6
                overridePendingTransition(0, 0);//7
                startActivity(intent);//8

            }
        });
        popupMenu.show();
    }
}
