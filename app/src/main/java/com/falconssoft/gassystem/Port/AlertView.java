package com.falconssoft.gassystem.Port;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.falconssoft.gassystem.R;


public class AlertView
{
	private static Context context;
//	Context context;
	public static void showError(String message, Context ctx)
	{
		showAlert("Error", message, ctx);
	}
	
	public static void showAlert(String message, Context ctx)
	{
		showAlert("Alert", message, ctx);
	}
	
	@SuppressLint("SetTextI18n")
	public static void showAlert(String title, String message, final Context ctx)
	{
		context=ctx;
		//Create a builder
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		builder.setMessage(message);
		//add buttons and listener
		EmptyListener pl = new EmptyListener();
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
//					BluetoothConnectMenu bb = new BluetoothConnectMenu();
//					bb.finishDialog();
//				Log.e("master","123456789");
			}
		});
		//Create the dialog
		AlertDialog ad = builder.create();
		//show
		ad.show();
//		final Dialog dialogAlert = new Dialog(ctx);
//		dialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialogAlert.setCancelable(false);
//		dialogAlert.setContentView(R.layout.alert_dialog_not_found);
//		dialogAlert.setCanceledOnTouchOutside(false);
//		TextView textMessage = (TextView) dialogAlert.findViewById(R.id.alertMessage);
//		Button exitAlert = (Button) dialogAlert.findViewById(R.id.exitAlert);
//		textMessage.setText(title+"\n\n"+message);
//
//		dialogAlert.show();
//
//		exitAlert.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialogAlert.dismiss();			}
//		});
//
//



	}
}

class EmptyListener implements DialogInterface.OnClickListener
{
   @Override
   public void onClick(DialogInterface dialog, int which)
   {

   }
}
