package anton.fons.bugz.android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector3;

import anton.fons.bugz.Game;

public class StepManager implements SensorEventListener
{
    private static float stepAmountThreshold = 2.5f;

    private static float lowPassFilterCuttoff = 0.9f;

    private static long initialTime = -1;
    private static int timeUntilLowPassFilterStabilized = 5000;
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
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onResume()
    {
        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause()
    {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if(initialTime < 0) initialTime = System.currentTimeMillis();
        getActualAcceleration(sensorEvent); //Anem calibrant el lowPassFilter
        if(System.currentTimeMillis() - initialTime < timeUntilLowPassFilterStabilized) return;
        if(!AndroidLauncher.game.created) return;

        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            if(System.currentTimeMillis() - lastStepTimestamp >= timeBetweenSteps)
            {
                Vector3 acc = getActualAcceleration(sensorEvent);
                float stepAmount = Math.abs( acc.len() );
                if(stepAmount >= stepAmountThreshold)
                {
                    ++steps;
                    AndroidLauncher.game.onStepDone();
                    lastStepTimestamp = System.currentTimeMillis();
                }
            }
        }
        //AndroidLauncher.game.AndroidResolver.log("Steps: " + steps);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }


    private Vector3 getActualAcceleration(SensorEvent e)
    {
        gravity.x = lowPassFilterCuttoff * gravity.x + (1 - lowPassFilterCuttoff) * e.values[0];
        gravity.y = lowPassFilterCuttoff * gravity.y + (1 - lowPassFilterCuttoff) * e.values[1];
        gravity.z = lowPassFilterCuttoff * gravity.z + (1 - lowPassFilterCuttoff) * e.values[2];

        Vector3 acc = new Vector3();
        acc.x = e.values[0] - gravity.x;
        acc.y = e.values[1] - gravity.y;
        acc.z = e.values[2] - gravity.z;

        return acc;
    }
}
