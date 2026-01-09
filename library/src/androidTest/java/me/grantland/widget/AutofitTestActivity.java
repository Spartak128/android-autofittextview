package me.grantland.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class AutofitTestActivity extends Activity {

    private FrameLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoot = new FrameLayout(this);
        setContentView(mRoot);
    }

    public AutofitTextView createAutofitTextView() {
        mRoot.removeAllViews();
        AutofitTextView view = new AutofitTextView(this);
        mRoot.addView(view);
        return view;
    }
}
