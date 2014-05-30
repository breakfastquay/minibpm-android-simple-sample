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

import java.nio.ByteBuffer;

import com.breakfastquay.minibpm.MiniBpm;

public class MiniBpmExampleActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	try {
	    InputStream audio = getResources().openRawResource(R.raw.a);
	    ByteArrayOutputStream ostr = new ByteArrayOutputStream(audio.available());
	    byte[] buf = new byte[1024];
	    int r;
	    while ((r = audio.read(buf)) >= 0) ostr.write(buf, 0, r);
	    byte[] raw = ostr.toByteArray();
	
	    // We assume raw is interleaved 2-channel 16-bit PCM
	    int channels = 2;
	    int frames = raw.length / (channels * 2);
	    int rate = 44100;

	    float[] insamples = new float[frames];

	    ByteBuffer bb = ByteBuffer.wrap(raw).order(null);

	    for (int i = 0; i < frames; ++i) {
		for (int c = 0; c < channels; ++c) {
		    short val = bb.getShort();
		    insamples[i] += ((float)val / 32768) / channels;
		}
	    }

	    long start = System.currentTimeMillis();

	    MiniBpm mbpm = new MiniBpm(rate);
	    double tempo = mbpm.estimateTempoOfSamples(insamples, 0, frames);
	    double[] candidates = mbpm.getTempoCandidates();
	    mbpm.dispose();

	    long end = System.currentTimeMillis();
	    double t = (double)(end - start) / 1000;

	    Log.d("MiniBpm", "estimated tempo of " + frames + "-frame stereo track as " + tempo + "bpm in " + t + "s (processing " + (frames / t) + "fps)");
	    Log.d("MiniBpm", "candidate tempi follow...");
	    for (int i = 0; i < candidates.length; ++i) {
		Log.d("MiniBpm", "candidate " + i + ": " + candidates[i]);
	    }

	} catch (IOException e) { }

	setContentView(R.layout.main);
    }
}
