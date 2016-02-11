package com.heartbeatmonitor;

import android.util.Log;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.Charset;

public class DataLayerListenerService extends WearableListenerService {

    private static final String LOG_TAG = "WEARABLE_LISTENER";

    private static WearEventHandler mHandler = null;
    public static void setHandler(WearEventHandler handler) {
        mHandler = handler;
    }

    /**
     * Notification that a peer is now reachable by this node
     * @param peer
     */
    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);

        String id = peer.getId();
        String name = peer.getDisplayName();

        Log.d(LOG_TAG, "Connected peer name & ID: " + name + "|" + id);
    }

    /**
     * Receives message events
     * @param messageEvent
     */
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        byte[] data = messageEvent.getData();
        String message = new String(data, Charset.forName("UTF-8"));
        String path = messageEvent.getPath();

        Log.d(LOG_TAG, "received a message from wear: " + path + " / " + message);
        // if a handler is registered, send the value as new message
        if(mHandler!=null) mHandler.onEvent(message);
    }


}