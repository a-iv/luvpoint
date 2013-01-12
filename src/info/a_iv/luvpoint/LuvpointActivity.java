package info.a_iv.luvpoint;

import info.a_iv.luvpoint.demo.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LuvpointActivity extends Activity {

	private static final String SAVED_URL = "info.a_iv.luvpoint.LuvpointActivity.SAVED_URL";

	private static final String HOST = "demo.luvpoint.com";
	private static final String START_URL = "http://" + HOST
			+ "/?template=lpmobile";

	private static final int OPTION_MENU_REFRESH_ID = 0x00;
	private static final int OPTION_MENU_HOME_ID = 0x01;
	private static final int OPTION_MENU_BACK_ID = 0x02;
	private static final int OPTION_MENU_FORWARD_ID = 0x03;
	private static final int OPTION_MENU_CLOSE_ID = 0x04;

	private class HostedWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (Uri.parse(url).getHost().equals(HOST)) {
				return false;
			}
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);
			return true;
		}

	}

	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		webView = (WebView) findViewById(R.id.webview);
		String url;
		if (savedInstanceState != null)
			url = savedInstanceState.getString(SAVED_URL);
		else
			url = START_URL;
		webView.loadUrl(url);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSavePassword(true);
		webView.setWebViewClient(new HostedWebViewClient());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SAVED_URL, webView.getUrl());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		menu.add(0, OPTION_MENU_REFRESH_ID, 0, getText(R.string.refresh))
				.setIcon(R.drawable.ic_menu_refresh);
		menu.add(0, OPTION_MENU_HOME_ID, 0, getText(R.string.home)).setIcon(
				R.drawable.ic_menu_home);
		menu.add(0, OPTION_MENU_BACK_ID, 0,
				getResources().getText(R.string.back))
				.setIcon(R.drawable.ic_menu_back)
				.setEnabled(webView.canGoBack());
		menu.add(0, OPTION_MENU_FORWARD_ID, 0,
				getResources().getText(R.string.forward))
				.setIcon(R.drawable.ic_menu_forward)
				.setEnabled(webView.canGoForward());
		menu.add(0, OPTION_MENU_CLOSE_ID, 0,
				getResources().getText(R.string.close)).setIcon(
				android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case OPTION_MENU_REFRESH_ID:
			webView.reload();
			return true;
		case OPTION_MENU_HOME_ID:
			webView.loadUrl(START_URL);
			return true;
		case OPTION_MENU_BACK_ID:
			webView.goBack();
			return true;
		case OPTION_MENU_FORWARD_ID:
			webView.goForward();
			return true;
		case OPTION_MENU_CLOSE_ID:
			finish();
			return true;
		}
		return false;
	}

}