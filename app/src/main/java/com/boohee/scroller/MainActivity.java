package com.boohee.scroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mScrollBtn, mFlingBtn, mOverScrollBtn, mOverFlingBtn, mOverSpringBtn;
    private ScrollTextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTextView = (ScrollTextView) findViewById(R.id.stv_view);
        mScrollBtn = (Button) findViewById(R.id.bt_scroller_scroll);
        mScrollBtn.setOnClickListener(onClickListener);
        mFlingBtn = (Button) findViewById(R.id.bt_scroller_fling);
        mFlingBtn.setOnClickListener(onClickListener);
        mOverScrollBtn = (Button) findViewById(R.id.bt_over_scroller_scroll);
        mOverScrollBtn.setOnClickListener(onClickListener);
        mOverFlingBtn = (Button) findViewById(R.id.bt_over_scroller_fling);
        mOverFlingBtn.setOnClickListener(onClickListener);
        mOverSpringBtn = (Button) findViewById(R.id.bt_over_scroller_spring);
        mOverSpringBtn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_scroller_scroll:
                    mTextView.startScrollerScroll();
                    break;
                case R.id.bt_scroller_fling:
                    mTextView.startScrollerFling();
                    break;
                case R.id.bt_over_scroller_scroll:
                    mTextView.startOverScrollerScroll();
                    break;
                case R.id.bt_over_scroller_fling:
                    mTextView.startOverScrollerFling();
                    break;
                case R.id.bt_over_scroller_spring:
                    mTextView.startOverScrollerSpringBack();
                    break;
            }
        }
    };
}
