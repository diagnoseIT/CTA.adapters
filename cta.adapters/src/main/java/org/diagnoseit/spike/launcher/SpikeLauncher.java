package org.diagnoseit.spike.launcher;

import org.diagnoseit.spike.monitoring.datamocking.DataGenerator;
import org.diagnoseit.spike.rules.processing.DiagnoseIT;
import org.diagnoseit.spike.trace.builder.MonitoringDataProcessing;

public class SpikeLauncher {
	
public static void main(String[] args) {
	
	//DiagnoseIT.getInstance().start();
	
	
	MonitoringDataProcessing.getInstance().start();

	DataGenerator.getInstance().start();
	//TODO: start Monitoring Data Generator
	
}
}
