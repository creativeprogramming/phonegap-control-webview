package de.fastr.phonegap.plugins;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.LocationManager;
import android.content.Context;
import android.app.Activity;
import android.view.View;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaActivity;
/*
 * thx to http://stackoverflow.com/questions/843675/how-do-i-find-out-if-the-gps-of-an-android-device-is-enabled
 */
public class ControlWebview extends CordovaPlugin{
	protected CordovaWebView controlView;
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		boolean success = false;
		if (action.equals("init")){
			success = this.init();
		}else if (action.equals("loadUrl")){
			String url = args.getString(0);
			success = this.loadUrlInMainView(url);
		}else if (action.equals("javascript")){
			String script = args.getString(0);
			success = this.javascript(script);
		}
		if (success){
			callbackContext.success();
		}else{
			callbackContext.error(0);
		}
		return success;
	}

	private boolean init(){
   	final CordovaActivity activity = (CordovaActivity) this.cordova.getActivity();
		final Context context = activity.getApplicationContext();
		(activity).runOnUiThread(new Runnable() {
    	@Override
    	public void run() {
				controlView = new CordovaWebView(activity);
				controlView.setId(200);
				controlView.setVisibility(View.INVISIBLE);
				controlView.loadUrl("file:///android_asset/www/control.html");
    	}
		});
				return true;
  }
	private boolean loadUrlInMainView(final String url){
   	final CordovaActivity activity = (CordovaActivity) this.cordova.getActivity();
		(activity).runOnUiThread(new Runnable() {
    	@Override
    	public void run() {
				activity.loadUrl(url);
    	}
		});
		return true;
  }
	private boolean javascript(String script){
		this.loadUrlInMainView(script);	
		return true;
  }
}
