package com.hellsam.example;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private SwipeRefreshLayout swipeRL;
	private ListView listView;
	private ArrayAdapter<String> adapter;

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			swipeRL.setRefreshing(false);
			adapter.clear();
			adapter.addAll(mockupData());
			adapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		swipeRL = (SwipeRefreshLayout) findViewById(R.id.swipeRL);
		listView = (ListView) findViewById(R.id.listView);
		swipeRL.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		
		swipeRL.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// 模拟网络请求数据
				new Thread() {
					public void run() {
						try {
							Thread.sleep(5000);
							myHandler.sendEmptyMessage(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					};
				}.start();
			}
		});
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mockupData());
		listView.setAdapter(adapter);
	}

	private List<String> mockupData() {
		List<String> items = new ArrayList<String>();
		for (int i = 0; i < (int) (Math.random() * 10) + 1; i++) {
			items.add("博客: hellsam.com");
			items.add("微信公众号: 菜鸟的天堂");
		}
		return items;
	}

}
