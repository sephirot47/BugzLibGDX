package anton.fons.bugz.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;

public class ConnectionManager extends BroadcastReceiver
{
    private BroadcastReceiver receiver;
    private final IntentFilter intentFilter = new IntentFilter();

    private Channel mChannel;
    private WifiP2pManager mManager;

    private Activity activity;

    public ConnectionManager()
    {
        activity = AndroidLauncher.activity;

        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager = (WifiP2pManager) activity.getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(activity, activity.getMainLooper(), null);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
        {
            // Determine if Wifi P2P mode is enabled or not, alert the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
            {
                //activity.setIsWifiP2pEnabled(true);
            } else
            {
                //activity.setIsWifiP2pEnabled(false);
            }
        }
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
        {
            // The peer list has changed!  We should probably do something about that.

        }
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed!  We should probably do something about that.

        }
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
        {
        }
    }

    public void onResume()
    {
        //receiver = new Broadc(mManager, mChannel, this);
        activity.registerReceiver(this, intentFilter);
    }

    public void onPause()
    {
        activity.unregisterReceiver(this);
    }
}
