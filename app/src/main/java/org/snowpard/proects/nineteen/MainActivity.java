package org.snowpard.proects.nineteen;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	private TextView txt_hello;
	private Button btn_en, btn_ru, btn_fr, btn_de;
	private Locale myLocale;    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		this.txt_hello = (TextView)findViewById(R.id.txt_hello);
		this.btn_en = (Button)findViewById(R.id.btn_en);
		this.btn_ru = (Button)findViewById(R.id.btn_ru);
		this.btn_fr = (Button)findViewById(R.id.btn_fr);
		this.btn_de = (Button)findViewById(R.id.btn_de);
		
		this.btn_en.setOnClickListener(this);
		this.btn_ru.setOnClickListener(this);
		this.btn_fr.setOnClickListener(this);
		this.btn_de.setOnClickListener(this);
		
		loadLocale();
	}
	
	public void loadLocale()
    {
    	String langPref = "Language";
    	SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
    	String language = prefs.getString(langPref, "");
    	changeLang(language);
    }
    
    public void saveLocale(String lang)
    {
    	String langPref = "Language";
    	SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
		editor.putString(langPref, lang);
		editor.commit();
    }

	public void changeLang(String lang)
    {
    	if (lang.equalsIgnoreCase(""))
    		return;
    	myLocale = new Locale(lang);
    	saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
    }
	
	private void updateTexts()
	{
		txt_hello.setText(R.string.hello_world);
		btn_en.setText(R.string.btn_en);
		btn_ru.setText(R.string.btn_ru);
		btn_fr.setText(R.string.btn_fr);
		btn_de.setText(R.string.btn_de);
	}

	@Override
	public void onClick(View v) {
		String lang = "en";
		switch (v.getId()) {
		case R.id.btn_en:
			lang = "en";
			break;
		case R.id.btn_ru:
			lang = "ru";
			break;
		case R.id.btn_de:
			lang = "de";
			break;
		case R.id.btn_fr:	
			lang = "fr";
			break;			
		default:
			break;
		}
		changeLang(lang);
	}
	
	@Override
	public void onConfigurationChanged(android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (myLocale != null){
	        newConfig.locale = myLocale;
	        Locale.setDefault(myLocale);
	        getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
	    }
	}
}
