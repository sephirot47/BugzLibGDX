package anton.fons.bugz.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ConnectionManager extends BroadcastReceiver
{
    private BroadcastReceiver receiver;
    private final IntentFilter intentFilter = new IntentFilter();

    private Channel mChannel;
    private WifiP2pManager mManager;

    private ArrayList<String> peers;
    private PeerListListener peerListListener;


    private Activity activity;

    public ConnectionManager()
    {
        activity = AndroidLauncher.activity;
        peers = new ArrayList<String>();
        final ConnectionManager pseudoThis = this; //To be able to call it from the peerListListener
        peerListListener = new PeerListListener()
        {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList)
            {
                pseudoThis.onPeersAvailable(wifiP2pDeviceList);
            }
        };

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

        AndroidLauncher.androidResolver.log( "create connectionManager" );
        initiateDiscoverPeers();
    }

    private void initiateDiscoverPeers()
    {
        AndroidLauncher.androidResolver.log( "initiateDiscoverPeers" );
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess()
            {
                AndroidLauncher.androidResolver.log( "onSuccess" );
                mManager.requestPeers(mChannel, peerListListener);
            }

            @Override
            public void onFailure(int reasonCode)
            {
                AndroidLauncher.androidResolver.log( "onFailure" );
            }
        });
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        AndroidLauncher.androidResolver.log( "onReceive" );
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
        {
            AndroidLauncher.androidResolver.log( "WIFI_P2P_STATE_CHANGED_ACTION" );

            // Determine if Wifi P2P mode is enabled or not, alert the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
            {
                //activity.setIsWifiP2pEnabled(true);
                AndroidLauncher.androidResolver.log( "WIFI_P2P_STATE_ENABLED" );
            }
            else
            {
                //activity.setIsWifiP2pEnabled(false);
                AndroidLauncher.androidResolver.log( "WIFI_P2P_STATE_DISABLED" );
            }
        }
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
        {
            // The peer list has changed!  We should probably do something about that.
            AndroidLauncher.androidResolver.log( "WIFI_P2P_PEERS_CHANGED_ACTION" );

        }
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed!  We should probably do something about that.
            AndroidLauncher.androidResolver.log( "WIFI_P2P_CONNECTION_CHANGED_ACTION" );

        }
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
        {
            AndroidLauncher.androidResolver.log( "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION" );
        }
    }

    public void onPeersAvailable(WifiP2pDeviceList peersList)
    {
        AndroidLauncher.androidResolver.log( "onPeersAvailable_____________" );
        peers.clear();
        Iterator i = peersList.getDeviceList().iterator();
        while(i.hasNext())
        {
            WifiP2pDevice dev = ((WifiP2pDevice)i.next());
            AndroidLauncher.androidResolver.log(dev.deviceAddress);
            peers.add(dev.deviceAddress);
        }
        AndroidLauncher.androidResolver.log( "___________________________" );
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
