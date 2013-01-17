package clue.api;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.SwingUtilities;

public class EventBroker {

	HashMap<String, ArrayList<Subscriber>> subscribers;

	public EventBroker() {
		subscribers = new HashMap<String, ArrayList<Subscriber>>();
	}

	public void registerEvent(String event) {
		if (subscribers.containsKey(event)) {
			System.out.println("You put that there already.  Stop it.");
		} else {
			subscribers.put(event, new ArrayList<Subscriber>());
		}
	}

	public void registerSubscriber(String event, Subscriber sub) {
		if (subscribers.containsKey(event)) {
			subscribers.get(event).add(sub);
		} else {
			System.out
					.println("Error: Tried to add subsriber to event that is not registered");
		}
	}

	public void notify(final String event) {
		for (final Subscriber s : subscribers.get(event)) {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						s.notify(event);
					}

				});
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				System.err.println("Event: " + event);
				e.printStackTrace();
			}
		}
	}

	public void deregisterEvent(String event) {
		if (subscribers.containsKey(event)) {
			subscribers.remove(event);
		}
	}

	public void deregisterSubscriber(Subscriber sub) {
		Collection<ArrayList<Subscriber>> a = subscribers.values();
		for (ArrayList<Subscriber> list : a) {
			if (list.contains(sub)) {
				a.remove(sub);
			}
		}

	}
}
