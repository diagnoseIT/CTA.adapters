package org.diagnoseit.spike.launcher;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TraceTriggerGui {
	public TraceTriggerGui() {
		JFrame guiFrame = new JFrame();
		// make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Trace Trigger GUI");
		guiFrame.setSize(250, 100); // This

		guiFrame.setLocationRelativeTo(null);

		final JButton nextTraceButton = new JButton("Next Trace");
		JLabel label = new JLabel("Current Trace ID:");
		final JLabel label_2 = new JLabel("-");

		nextTraceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					long traceID = SpikeLauncher.nextTrace();
					label_2.setText(String.valueOf(traceID));
				} catch (IllegalStateException e) {
					label_2.setText("No trace available anymore");
					nextTraceButton.setEnabled(false);
				}
				
			}
		});

		guiFrame.add(label, BorderLayout.NORTH);
		guiFrame.add(label_2, BorderLayout.CENTER);
		guiFrame.add(nextTraceButton, BorderLayout.SOUTH);

		guiFrame.setVisible(true);
	}

}
