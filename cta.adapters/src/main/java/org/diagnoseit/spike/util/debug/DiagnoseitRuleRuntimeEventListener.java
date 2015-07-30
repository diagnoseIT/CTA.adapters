/**
 * 
 */
package org.diagnoseit.spike.util.debug;

import java.io.PrintStream;
import java.util.StringTokenizer;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;

/**
 * @author Okanovic
 *
 */
public class DiagnoseitRuleRuntimeEventListener implements
		RuleRuntimeEventListener {
	private PrintStream stream;

	public DiagnoseitRuleRuntimeEventListener() {
		this.stream = System.err;
	}

	public DiagnoseitRuleRuntimeEventListener(PrintStream stream) {
		this.stream = stream;
	}

	public void objectInserted(ObjectInsertedEvent event) {
		StringTokenizer st = new StringTokenizer(event.getObject().toString(),
				"\n");
		st.nextToken();
		String print = "Inserted object: " + st.nextToken();
		stream.println(print);
	}

	public void objectDeleted(ObjectDeletedEvent event) {
		StringTokenizer st = new StringTokenizer(event.getOldObject()
				.toString(), "\n");
		st.nextToken();
		String print = "Deleted object: " + st.nextToken();
		stream.println(print);
	}

	public void objectUpdated(ObjectUpdatedEvent event) {
		StringTokenizer st = new StringTokenizer(event.getObject().toString(),
				"\n");
		st.nextToken();
		String print = "Updated object: " + st.nextToken();
		stream.println(print);
	}
}