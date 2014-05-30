package com.breakfastquay.minibpmexample;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.content.res.Resources;
import android.util.Log;
import android.util.TimingLogger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;

import com.breakfastquay.minibpm.MiniBpm;

public class MiniBpmExampleActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	setContentView(R.layout.main);
    }
}
