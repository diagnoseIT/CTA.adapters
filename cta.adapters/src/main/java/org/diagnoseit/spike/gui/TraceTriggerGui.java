package org.diagnoseit.spike.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import org.diagnoseit.spike.launcher.SpikeLauncher;
import org.diagnoseit.spike.trace.Trace;

public class TraceTriggerGui {
	private Trace currentTrace;
	boolean showingTrace = false;
	JScrollPane tracePane = null;
	public TraceTriggerGui() {
		final JFrame guiFrame = new JFrame();
		// make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Trace Trigger GUI");
		guiFrame.setSize(500, 200); // This
		guiFrame.setLayout(new BorderLayout());
		guiFrame.setLocationRelativeTo(null);

	
		final JPanel topPanel = new JPanel(new FlowLayout());
		topPanel.setSize(500, 200);


		final JButton nextTraceButton = new JButton("Next Trace");
		final JButton showTraceButton = new JButton("View Trace");
		showTraceButton.setEnabled(false);
		JLabel label = new JLabel("Current Trace ID:");
		final JLabel label_2 = new JLabel("-");

		nextTraceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					currentTrace = SpikeLauncher.nextTrace();
					label_2.setText(String.valueOf(currentTrace.getLogicalTraceId()));
					showTraceButton.setEnabled(true);
					if(showingTrace){
						guiFrame.remove(tracePane);
						tracePane =  new JScrollPane(new JTree(new TraceTreeNode(currentTrace.getRoot().getRoot())));

						tracePane.setVisible(true);
						guiFrame.add(tracePane, BorderLayout.CENTER);
					}
				} catch (IllegalStateException e) {
					currentTrace = null;
					label_2.setText("No trace available anymore");
					nextTraceButton.setEnabled(false);
					showTraceButton.setEnabled(false);
				}

			}
		});

		showTraceButton.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent event) {
				if (showingTrace) {
					showingTrace = false;
					guiFrame.remove(tracePane);

					guiFrame.setSize(guiFrame.getSize().width, 200);

					showTraceButton.setText("View Trace");
				} else {
					showingTrace = true;
					if (currentTrace != null) {
						tracePane =  new JScrollPane(new JTree(new TraceTreeNode(currentTrace.getRoot().getRoot())));

						tracePane.setVisible(true);
						guiFrame.add(tracePane, BorderLayout.CENTER);
						
						guiFrame.setSize(guiFrame.getSize().width, 800);
						showTraceButton.setText("Hide Trace");
					}
				}

			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(nextTraceButton);
		buttonPanel.add(showTraceButton);
		topPanel.add(label);
		topPanel.add(label_2);
		topPanel.add(buttonPanel);
		guiFrame.add(topPanel, BorderLayout.PAGE_START);
		
		guiFrame.setVisible(true);
	}

}
