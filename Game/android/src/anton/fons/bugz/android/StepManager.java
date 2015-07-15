package anton.fons.bugz.android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector3;

import anton.fons.bugz.Game;

public class StepManager implements SensorEventListener
{
    private static float stepAmountThreshold = 0.5f;
    private static int timeBetweenSteps = 700; //ms
    private long lastStepTimestamp = -999;

    private Vector3 gravity;
    private SensorManager sensorManager;
    private Sensor senAccelerometer, senGravity;

    private int steps = 0;

    public StepManager()
    {
        gravity = Vector3.Zero;

        sensorManager = (SensorManager) AndroidLauncher.activity.getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        senGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, senGravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onResume()
    {
        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, senGravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause()
    {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
        {
            if(System.currentTimeMillis() - lastStepTimestamp >= timeBetweenSteps)
            {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                Vector3 acc = new Vector3(x,y,z);

                //We project the acceleration onto the normalized gravity vector
                float stepAmount = Math.abs(acc.dot(gravity) );
                if(stepAmount >= stepAmountThreshold)
                {
                    ++steps;
                    Game.onStepDone();
                    lastStepTimestamp = System.currentTimeMillis();
                }
            }

        }
        else if (mySensor.getType() == Sensor.TYPE_GRAVITY)
        {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            gravity = new Vector3(x, y, z);
            gravity.nor();

            //Log.d("Bugz", "( " + x + ", " + y + ", " + z + " )");
        }

        Game.screenLog("Steps: " + steps);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}
