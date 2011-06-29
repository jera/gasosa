package br.com.jera.gasosa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import br.com.jeramobstats.JeraAgent;

import com.xtify.android.sdk.PersistentLocationManager;

public class GasosaActivity extends Activity {

	public static final String PREFS_NAME = "br.com.jera.gasosa.Config";

    private PersistentLocationManager persistentLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // xtify-specific code
        Context context = this;
        persistentLocationManager = new PersistentLocationManager(context);
        Thread xtifyThread = new Thread(new Runnable() {
            public void run()
            {
                persistentLocationManager.setNotificationIcon(R.drawable.notification);
                persistentLocationManager.setNotificationDetailsIcon(R.drawable.icon);
                boolean trackLocation = persistentLocationManager.isTrackingLocation();
                boolean deliverNotifications = persistentLocationManager.isDeliveringNotifications();
                if (trackLocation || deliverNotifications)
                {
                    persistentLocationManager.startService();
                }
            }
        });
        xtifyThread.start(); // to avoid Android's application-not-responding dialog box,
                             // do non-essential work in another thread

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        JeraAgent.onStartSession(this, "QI4YUGV5K7FN7I42RPA1");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        JeraAgent.onEndSession(this);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.config: {
			startActivity(new Intent(this, Config.class));
			return true;
		}
		case R.id.principal: {
			startActivity(new Intent(this, Principal.class));
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
