/**
 * 
 */
package org.diagnoseit.spike.util.debug;

import java.io.PrintStream;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;

/**
 * @author Okanovic
 *
 */
public class DiagnoseitAgendaEventListener implements AgendaEventListener {
	private PrintStream stream;

	public DiagnoseitAgendaEventListener() {
		this.stream = System.err;
	}

	public DiagnoseitAgendaEventListener(PrintStream stream) {
		this.stream = stream;
	}

	@Override
	public void matchCreated(MatchCreatedEvent event) {
		String print = "Match created for rule: "
				+ event.getMatch().getRule().getName();
		stream.println(print);
	}

	@Override
	public void matchCancelled(MatchCancelledEvent event) {
		String print = "Match cancelled for rule: "
				+ event.getMatch().getRule().getName();
		stream.println(print);
	}

	@Override
	public void beforeMatchFired(BeforeMatchFiredEvent event) {
		// String print = "Before match fired: " + event.getMatch().getRule();
		// stream.println(print);
	}

	@Override
	public void afterMatchFired(AfterMatchFiredEvent event) {
		// String print = "After match fired: " + event.getMatch().getRule();
		// stream.println(print);
	}

	@Override
	public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
		String print = "Agenda group popped: "
				+ event.getAgendaGroup().getName();
		stream.println(print);
	}

	@Override
	public void agendaGroupPushed(AgendaGroupPushedEvent event) {
		String print = "Agenda group pushed: "
				+ event.getAgendaGroup().getName();
		stream.println(print);
	}

	@Override
	public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
		String print = "Before rule flow group activated: "
				+ event.getRuleFlowGroup().getName();
		stream.println(print);
	}

	@Override
	public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
		String print = "After rule flow group activated: "
				+ event.getRuleFlowGroup().getName();
		stream.println(print);
	}

	@Override
	public void beforeRuleFlowGroupDeactivated(
			RuleFlowGroupDeactivatedEvent event) {
		String print = "Before rule flow group deactivated: "
				+ event.getRuleFlowGroup().getName();
		stream.println(print);
	}

	@Override
	public void afterRuleFlowGroupDeactivated(
			RuleFlowGroupDeactivatedEvent event) {
		String print = "After rule flow group deactivated: "
				+ event.getRuleFlowGroup().getName();
		stream.println(print);
	}
}