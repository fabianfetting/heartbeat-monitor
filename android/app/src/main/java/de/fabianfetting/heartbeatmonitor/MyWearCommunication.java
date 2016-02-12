package de.fabianfetting.heartbeatmonitor;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class MyWearCommunication extends ReactContextBaseJavaModule {

    public MyWearCommunication(ReactApplicationContext reactContext) {
        super(reactContext);

        WearEventHandler mEventHandler = createEventHandler(reactContext);
        registerEventHandler(mEventHandler);
    }

    private WearEventHandler createEventHandler(ReactApplicationContext reactContext) {
        return new WearEventHandler(reactContext);
    }

    private void registerEventHandler(WearEventHandler handler) {
        DataLayerListenerService.setHandler(handler);
    }

    /**
     * @return represents this class in JavaScript
     */
    @Override
    public String getName() {
        return "MyWearCommunication";
    }

}
