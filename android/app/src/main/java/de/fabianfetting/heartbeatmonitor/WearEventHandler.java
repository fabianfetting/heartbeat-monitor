package de.fabianfetting.heartbeatmonitor;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class WearEventHandler {

    private ReactApplicationContext mReactContext;

    public WearEventHandler(ReactApplicationContext context) {
        mReactContext = context;
    }

    public void onEvent(String msg) {
        Log.d("WEAR_EVENT_HANDLER", "on event: " + msg);

        mReactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("heartrateChanged", msg);
    }

}
