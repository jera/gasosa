package br.com.jera.gasosa;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import br.com.jeramobstats.JeraAgent;


public class SplashDialog extends Dialog {

	private Activity activity;
	private final String PATH = "/data/data/br.com.jera.gasosa/";
	private final String PATH_URL = "http://games.jera.com.br/splash/gasosa/";

	public SplashDialog(final Activity activity) {
		super(activity);
		this.activity = activity;
		if (isOnline()) {
			AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					checkUpdates();
					return null;
				}
			};
			task.execute((Void[]) null);
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		update();
	}

	public boolean isSplashed() {
		return Preferences.readBoolean(activity, "splashed");
	}

	public void setSplashed(Boolean splashed) {
		Preferences.write(activity, "splashed", splashed);
	}

	public void update() {
		setContentView(R.layout.dynamic_splash);

		ImageView image = (ImageView) findViewById(R.id.splash);
		BitmapDrawable bd = new BitmapDrawable(activity.getResources(), BitmapFactory.decodeFile(PATH + "splash.jpg"));
		if (new File(PATH + "splash.jpg").exists()) {
			image.setBackgroundDrawable(bd);
		} else {
			image.setImageResource(R.drawable.splash);
			Preferences.write(activity, "splash_link", "https://market.android.com/details?id=br.com.jera.vikings");
		}
		if (Preferences.readString(activity, "splash_link") != null) {
			image.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					activity.runOnUiThread(new Runnable() {
						public void run() {
							JeraAgent.logEvent("SPLASH_CLICK");
							Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Preferences.readString(activity, "splash_link")));
							activity.startActivity(intent);
						}
					});
				}
			});
		}

		findViewById(R.id.close_splash).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	private void checkUpdates() {
		downloadFromUrl("splash.jpg", "splash.jpg");
		File file = new File("splash.jpg");
		File web = new File(PATH + "tmp_" + file);
		File old = new File(PATH + file);
		if (!old.exists() || !isEquals(web, old)) {
			old.delete();
			web.renameTo(old);
			setSplashed(false);
			try {
				downloadFromUrl("splash.dat", "splash.dat");
				DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(PATH + "tmp_splash.dat"))));
				Preferences.write(activity, "splash_link", dis.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void downloadFromUrl(String imageURL, String fileName) {
		try {
			URL url = new URL(PATH_URL + imageURL);
			File file = new File(fileName);

			long startTime = System.currentTimeMillis();
			Log.d("ImageManager", "download begining");
			Log.d("ImageManager", "download url:" + url);
			Log.d("ImageManager", "downloaded file name:" + fileName);
			URLConnection ucon = url.openConnection();
			ucon.setConnectTimeout(5000);
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			FileOutputStream fos = new FileOutputStream(PATH + "tmp_" + file);
			fos.write(baf.toByteArray());
			fos.close();
			Log.d("ImageManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");

		} catch (IOException e) {
			Log.d("ImageManager", "Error: " + e);
		}
	}

	public boolean isEquals(File file1, File file2) {
		try {
			FileInputStream f1 = new FileInputStream(file1);
			FileInputStream f2 = new FileInputStream(file2);
			byte[] b1 = new byte[(int) file1.length()];
			byte[] b2 = new byte[(int) file2.length()];
			f1.read(b1);
			f2.read(b2);
			for (int j = 0; j < b1.length; j++) {
				if (b1[j] != b2[j])
					return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
