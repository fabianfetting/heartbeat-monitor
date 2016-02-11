package com.heartbeatmonitor;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.widget.TextView;

import java.util.List;

import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;

public class MainActivity extends WearableActivity implements SensorEventListener {

    private static final String LOG_TAG = "SMARTWATCH";

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private GoogleApiClient mGoogleApiClient;

    private Sensor mHeartRateSensor;
    private SensorManager mSensorManager;
    private Integer currentValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        mGoogleApiClient.connect();

        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mHeartRateSensor != null) {
            Log.d(LOG_TAG, "HEART RATE SENSOR NAME: " + mHeartRateSensor.getName() + " TYPE: " + mHeartRateSensor.getType());
            mSensorManager.unregisterListener(this, this.mHeartRateSensor);
            boolean isRegistered = mSensorManager.registerListener(this, this.mHeartRateSensor, SensorManager.SENSOR_DELAY_UI);
            Log.d(LOG_TAG, "HEART RATE LISTENER REGISTERED: " + isRegistered);
        } else {
            Log.d(LOG_TAG, "NO HEART RATE SENSOR");
        }

        sendMessageToHandheld("0");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE && sensorEvent.values.length > 0) {

            for(Float value : sensorEvent.values) {

                int newValue = Math.round(value);
                //Log.d(LOG_TAG, "#: " + newValue);
                if(currentValue != newValue) {
                    currentValue = newValue;
                    Log.d(LOG_TAG, "NEW VALUE: " + currentValue);
                    mTextView.setText(currentValue.toString());

                    sendMessageToHandheld(currentValue.toString());
                }

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSensorManager.unregisterListener(this);
        Log.d(LOG_TAG, "SENSOR UNREGISTERED");
    }

    /**
     * sends a string message to the connected handheld using the google api client (if available)
     * @param message
     */
    private void sendMessageToHandheld(final String message) {

        if (mGoogleApiClient == null)
            return;

        Log.d(LOG_TAG, "SEND MESSAGE TO HANDHELD: " + message);

        // use the api client to send the heartbeat value to our handheld
        final PendingResult<NodeApi.GetConnectedNodesResult> nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient);
        nodes.setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult result) {
                final List<Node> nodes = result.getNodes();
                if (nodes != null) {
                    for (int i = 0; i < nodes.size(); i++) {
                        final Node node = nodes.get(i);
                        Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), message, null);
                    }
                }
            }
        });

    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(android.R.color.black));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d(LOG_TAG, "ACCURACY CHANGED: " + i);
    }
}
