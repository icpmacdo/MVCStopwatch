/**
 * MVC Stopwatch app
 * Ian MacDonald (macd0719@algonquinlive.com)
 */

package com.algonquincollege.macd0719.mvcstopwatch;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import model.StopwatchModel;

public class MainActivity extends AppCompatActivity implements Observer {

    private FloatingActionButton fab;
    private static final String ABOUT_DIALOG_TAG = "About";
    private StopwatchModel mStopwatchModel;
    private DialogFragment mAboutDialog;
    private TextView mStopwatchView;
    private Runnable mUpdateStopwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStopwatchModel.isRunning()) {
                    mStopwatchModel.stop();
                } else {
                    mStopwatchModel.start();
                }

            }
        });

        mAboutDialog = new AboutDialogFragment();

        mStopwatchModel = new StopwatchModel();
        mStopwatchModel.addObserver(this);
        mStopwatchView = (TextView) findViewById(R.id.textViewStopwatch);
        mUpdateStopwatch = new Runnable() {
            @Override
            public void run() {
                mStopwatchView.setText(mStopwatchModel.toString());

                if (mStopwatchModel.isRunning()) {
                    fab.setImageResource(android.R.drawable.ic_media_pause);
                } else {
                    fab.setImageResource(android.R.drawable.ic_media_play);
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            mAboutDialog.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        if (id == R.id.action_reset) {

            mStopwatchModel.reset();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object data) {
        runOnUiThread(mUpdateStopwatch);

    }
}
