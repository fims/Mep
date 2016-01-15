package hevs.aislab.mep;


import android.content.Context;
import android.widget.Toast;

import ch.hevs.aislab.magpie.agent.MagpieAgent;
import ch.hevs.aislab.magpie.behavior.Behavior;
import ch.hevs.aislab.magpie.event.LogicTupleEvent;
import ch.hevs.aislab.magpie.event.MagpieEvent;

/**
 * Very simple example of a behavior in Java. The TestBehavior extends MAGPIEs Behavior.
 * This behavior checks if a value is below 10 or not. If the value is below 10 an
 * alert gets triggered. This alert gets directly executed on the UI thread.
 *
 */
public class TestBehaviour extends Behavior{

    /**
     * The value we compare the send value.
     */
    public static final double TEST_VALUE = 10;

    /**
     *
     * @param context Context
     * @param agent MagpieAgent
     * @param priority Priority
     */
    public TestBehaviour(Context context, MagpieAgent agent, int priority) {
        setContext(context);
        setAgent(agent);
        setPriority(priority);
    }

    /**
     * Gets called if this agent should handle the event. We can run the result directly on the
     * UI thread. We don't have to pass the result back to the MainActivity.
     *
     * @param magpieEvent MagpieEvent
     */
    @Override
    public void action(MagpieEvent magpieEvent) {
        LogicTupleEvent logicTupleEvent = (LogicTupleEvent) magpieEvent;

        double value = Double.parseDouble(logicTupleEvent.getArguments().get(0));
        if (value < TEST_VALUE) {
            ((MainActivity) getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String name = getAgent().getName();

                    Toast.makeText(getContext(), "Agent " + name + " has been triggered",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Checks if the triggered event should be handled by this agent or not.
     *
     * @param magpieEvent MagpieEvent
     * @return boolean If the Agent is triggered or not
     */
    @Override
    public boolean isTriggered(MagpieEvent magpieEvent) {
        LogicTupleEvent condition = (LogicTupleEvent) magpieEvent;
        return condition.getName().equals("test");
    }
}
