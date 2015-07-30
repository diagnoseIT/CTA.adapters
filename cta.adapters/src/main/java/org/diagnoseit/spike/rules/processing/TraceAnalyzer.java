package org.diagnoseit.spike.rules.processing;

import org.diagnoseit.spike.trace.Trace;
import org.diagnoseit.spike.util.debug.DiagnoseitAgendaEventListener;
import org.diagnoseit.spike.util.debug.DiagnoseitRuleRuntimeEventListener;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TraceAnalyzer {
	KieSession kSession;

	public TraceAnalyzer() {
		// TODO: set-up/configure DROOLS - done 21.07.2015.
		// TODO: investigate multiple sessions and threads

		// load up the knowledge base
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();

		// session is created only once
		kSession = kContainer.newKieSession("ksession-rules");

		// listens for before and after rules activation events - default impl
		kSession.addEventListener(new DiagnoseitAgendaEventListener());
		// listens for inserts and updates of objects events - default impl
		kSession.addEventListener(new DiagnoseitRuleRuntimeEventListener());
	}

	public void analyze(Trace trace) {
		// start DROOLS sessions etc.

		kSession.insert(trace);
		// go
		kSession.fireAllRules();
	}
}
