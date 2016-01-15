package hevs.aislab.mep;

import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

import ch.hevs.aislab.magpie.agent.MagpieAgent;
import ch.hevs.aislab.magpie.android.MagpieActivity;
import ch.hevs.aislab.magpie.behavior.PriorityBehaviorAgentMind;
import ch.hevs.aislab.magpie.behavior.SequentialBehaviorAgentMind;
import ch.hevs.aislab.magpie.environment.Environment;
import ch.hevs.aislab.magpie.environment.Services;
import ch.hevs.aislab.magpie.event.LogicTupleEvent;

/**
 * Very simple example for the MAGPIE library. We extend the MainActivity with the MagpieActivity.
 * This example does not use any sensor, so we don't need the sensorConnectionResult Method.
 * We create just one agent with a basic behaviour that checks if a value is below 10 or not. If
 * the value is below 10 an alert gets triggered.
 */
public class MainActivity extends MagpieActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Method called by the button trigger_event.
     * The TestBehaviour class triggers an event if the value is below 10.
     */
    public void triggerEvent() {
        LogicTupleEvent logicTupleEvent = new LogicTupleEvent("test", "1");
        logicTupleEvent.setTimeStamp(Calendar.getInstance().get(Calendar.MILLISECOND));
        Log.d("Testing/debugging", "Number of agents: " + Environment.getInstance().getRegisteredAgents().size());

        sendEvent(logicTupleEvent);
    }

    /**
     * Method called by the button dont_trigger_event
     * The TestBehaviour class triggers an event if the value is below 10.
     */
    public void dontTriggerEvent() {
        // event gets triggered if value is below 10
        LogicTupleEvent logicTupleEvent = new LogicTupleEvent("test", "10");
        logicTupleEvent.setTimeStamp(Calendar.getInstance().get(Calendar.MILLISECOND));
        triggerEvent();
        sendEvent(logicTupleEvent);
    }

    /**
     * Returns the status of the sensor. E.g.
     * <p>1) The device has no Bluetooth</p>
     * <p>2) The Bluetooth is not enabled on this device</p>
     * <p>3) The sensor is not connected</p>
     * <p>4) The sensor is connected</p>
     *
     * @param i int Status of the sensor
     */
    @Override
    protected void sensorConnectionResult(int i) {
        //we don't use a sensor in this example
    }

    /**
     * <p>
     * Gets called if the environment is successfully created. The environment gets created
     * just the first time it gets called. The environment gets serialized with all its agents
     * when the application gets terminated. When the application is started again the environment
     * gets loaded.
     * <p/>
     *
     * <p>All Agents are defined in this method.</p>
     *
     * <p>
     * <b>Once the application is deployed to the device it is not possible to add new agents.</b>
     * </p>
     *
     * <p>
     *     In this example we use a PriorityBehaviorAgendMind. The highest priority number gets
     *     executed first. There is also a SequentialBehaviorAgentMind. There the agents are
     *     executed in the order they have been defined.
     * </p>
     */
    @Override
    public void onEnvironmentConnected() {
        MagpieAgent behaviorAgent = new MagpieAgent("priority_agent", Services.LOGIC_TUPLE);
        PriorityBehaviorAgentMind behaviorMind = new PriorityBehaviorAgentMind();
        behaviorMind.addBehavior(new TestBehaviour(this, behaviorAgent, 0));
        behaviorAgent.setMind(behaviorMind);
        getService().registerAgent(behaviorAgent);
    }

    /**
     * This method handles the response if an alert gets triggered by a Prolog agent.
     * In this example we use just the Java method for using agents. So we don't need this method.
     *
     * @param logicTupleEvent LogicalTubleEvent The triggered event
     */
    @Override
    public void onAlertProduced(LogicTupleEvent logicTupleEvent) {
    }
}
