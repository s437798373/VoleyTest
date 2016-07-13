package com.example.voleytest;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv;
	private ImageView image;
	private RequestQueue mQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
		image = (ImageView) findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);
		// 获取一个消息队列
		mQueue = Volley.newRequestQueue(MainActivity.this);
		// getData("https://route.showapi.com/9-7?area=%E6%B7%B1%E5%9C%B3&areaid=&month=201601&showapi_appid=13168&showapi_timestamp=20160620154315&showapi_sign=337cbb029bce20eaac3df79ac5680dca");
		BaiDuMap();
	}
	//判断是否安装了百度i
	private boolean isInstallByread(String packageName) {   
	    return new File("/data/data/" + packageName).exists();   
	 }
	// 调用百度地图
	private void BaiDuMap() {
		try {
			Intent intent = Intent.getIntent(
					"intent://map/direction?origin=latlng:22.606447,114.124753|name:我的位置&destination=latlng:22.552654,113.953716|name:终点位置"
					+ "&mode=driving&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
			if (isInstallByread("com.baidu.BaiduMap")) {
				startActivity(intent); // 启动调用
				Log.e("GasStation", "百度地图客户端已经安装");
			} else {
				Log.e("GasStation", "没有安装百度地图客户端");
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	// 网络请求
	private void getData(String url) {
		// post请求
		JSONObject jSONObject = new JSONObject();
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jSONObject, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				tv.setText(arg0.toString());
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Log.e("tag", arg0.getMessage());
			}
		});

		// 添加进消息队列
		mQueue.add(jsonObjectRequest);
	}

	private void getASYNC(String url) {
		AsyncHttpClient client = new AsyncHttpClient();
		// com.loopj.android.http.AsyncHttpResponseHandler.AsyncHttpResponseHandler()
		// com.loopj.android.http.AsyncHttpResponseHandler.AsyncHttpResponseHandler()

		client.get(url, null, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.e("tag", new String(arg2).toString());
				tv.setText(new String(arg2).toString() + "-----");
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
			}

		});
	}
}
