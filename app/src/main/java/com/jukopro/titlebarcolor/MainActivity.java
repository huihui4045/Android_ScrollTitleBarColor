package com.jukopro.titlebarcolor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jukopro.titlebarcolor.ObservableScrollView.ScrollViewListener;

public class MainActivity extends Activity implements ScrollViewListener{

    private ObservableScrollView scrollView;
    
	private ListView listView;
	
	private ImageView imageView;
	
	private TextView textView;
	
	private int imageHeight;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (ObservableScrollView) findViewById(R.id.scrollview);
        listView = (ListView) findViewById(R.id.listview);
        imageView = (ImageView) findViewById(R.id.imageview);
        textView = (TextView) findViewById(R.id.textview);
        initListeners();
        initData();
    }
	
	private void initListeners() {
		// 获取顶部图片高度后，设置滚动监听
		ViewTreeObserver vto = imageView.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				imageView.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);
				imageHeight = imageView.getHeight();

				scrollView.setScrollViewListener(MainActivity.this);
			}
		});
	}



	private void initData() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.data));
		listView.setAdapter(adapter);
	}



	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		// TODO Auto-generated method stub
		// Log.i("TAG", "y--->" + y + "    height-->" + height);
		if (y <= 0) {
			textView.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//AGB由相关工具获得，或者美工提供
		} else if (y > 0 && y <= imageHeight) {
			float scale = (float) y / imageHeight;
			float alpha = (255 * scale);
			// 只是layout背景透明(仿知乎滑动效果)
			textView.setBackgroundColor(Color.argb((int) alpha, 227, 29, 26));
		} else {
			textView.setBackgroundColor(Color.argb((int) 255, 227, 29, 26));
		}
	}
}
