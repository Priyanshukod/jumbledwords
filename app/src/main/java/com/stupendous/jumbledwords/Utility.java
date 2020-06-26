package com.stupendous.jumbledwords;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.stupendous.jumbledwords.provider.JumbleDbSQLiteOpenHelper;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsCursor;
import com.stupendous.jumbledwords.provider.jumblewords.JumblewordsSelection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Utility 
{

	private static final String TAG = Utility.class.getName();

	public static void openAppSettings(Context context) {
		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", context.getPackageName(), null);
		intent.setData(uri);
		context.startActivity(intent);
	}

	public static void showPermissionRequiredDialog(final Context activity, String title, String msg, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder;// = new AlertDialog.Builder(activity);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
		} else {
			builder = new AlertDialog.Builder(activity);
		}

		builder.setCancelable(true);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton(R.string.go_to_app_settings, onClickListener);

		builder.show();
	}

	public static void copyDataBase(Context context,String path)
	{
		Log.e(TAG,"copying db to :"+path);

		byte[] buffer = new byte[1024];
		OutputStream myOutput = null;
		int length;
		// Open your local db as the input stream
		InputStream myInput = null;
		try
		{
			myInput = context.getApplicationContext().getAssets().open("words.db");
			// transfer bytes from the inputfile to the
			// outputfile
			File file = new File(path);

			file.setWritable(true);

			if(file.exists()){
				Log.e(TAG,"File already exist");
				file.delete();
			}
			SQLiteDatabase temp = JumbleDbSQLiteOpenHelper.getInstance(context).getReadableDatabase();
			       //gets readable database: creates databases folder containing DB_NAME db
			temp.close();
			myOutput =new FileOutputStream(path);

			while((length = myInput.read(buffer)) > 0)
			{
				myOutput.write(buffer, 0, length);
			}

			myOutput.flush();
			myOutput.close();
			myInput.close();

			file = new File(path);

			file.setWritable(true);

			Log.e(TAG, "New database has been copied to device!");

			JumblewordsCursor cursor = new JumblewordsSelection().query(context);

			if(cursor!=null){
				Log.e(TAG,"Count:"+cursor.getCount());
				if(cursor.getCount() > 0)
					AppPreferences.setBooleanSharedPreference(context,AppPreferences.KEY_DB_COPIED,true);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
