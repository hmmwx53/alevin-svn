/* ***** BEGIN LICENSE BLOCK *****
 * Copyright (C) 2010-2011, The VNREAL Project Team.
 * 
 * This work has been funded by the European FP7
 * Network of Excellence "Euro-NF" (grant agreement no. 216366)
 * through the Specific Joint Developments and Experiments Project
 * "Virtual Network Resource Embedding Algorithms" (VNREAL). 
 *
 * The VNREAL Project Team consists of members from:
 * - University of Wuerzburg, Germany
 * - Universitat Politecnica de Catalunya, Spain
 * - University of Passau, Germany
 * See the file AUTHORS for details and contact information.
 * 
 * This file is part of ALEVIN (ALgorithms for Embedding VIrtual Networks).
 *
 * ALEVIN is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License Version 3 or later
 * (the "GPL"), or the GNU Lesser General Public License Version 3 or later
 * (the "LGPL") as published by the Free Software Foundation.
 *
 * ALEVIN is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * or the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License and
 * GNU Lesser General Public License along with ALEVIN; see the file
 * COPYING. If not, see <http://www.gnu.org/licenses/>.
 *
 * ***** END LICENSE BLOCK ***** */
package vnreal.gui.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import mulavito.gui.dialogs.AbstractButtonDialog;
import vnreal.algorithms.AbstractAlgorithm;
import vnreal.algorithms.AlgorithmParameter;
import vnreal.algorithms.AvailableResources;
import vnreal.core.Scenario;
import vnreal.gui.GUI;
import vnreal.network.NetworkStack;

@SuppressWarnings("serial")
public class AvailableResourcesWizard extends AbstractButtonDialog implements
		ActionListener {
	private JPanel parPanel;
	private JSpinner maxDistSpinner;
	private JSpinner kSpinner;

	private JRadioButton yesIsDistanceButton;
	private JRadioButton noIsDistanceButton;
	static String yesIsDistanceString = "Yes";
	static String noIsDistanceString = "No";

	private JRadioButton yesNodeOverloadButton;
	private JRadioButton noNodeOverloadButton;
	static String yesNodeOverloadString = "Yes";
	static String noNodeOverloadString = "No";
	
	public AvailableResourcesWizard() {
		super(GUI.getInstance(), "Setting Parameters Wizard", "Submit",
				new Dimension(400, 300));
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == noIsDistanceButton) {
			maxDistSpinner.setValue(0);
			maxDistSpinner.setEnabled(false);
		} else {
			maxDistSpinner.setEnabled(true);
		}

	}

	@Override
	protected JPanel createContent() {
		JPanel content = new JPanel();

		// create a layout
		GroupLayout layout = new GroupLayout(content);
		content.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		SequentialGroup row = layout.createSequentialGroup();
		layout.setVerticalGroup(row);

		ParallelGroup col = layout.createParallelGroup();
		layout.setHorizontalGroup(col);

		// create the panels contained by the content panel

		// substrate network panel
		parPanel = new JPanel();
		parPanel.setBorder(BorderFactory.createTitledBorder("Parameters"));
		GroupLayout snLayout = new GroupLayout(parPanel);
		parPanel.setLayout(snLayout);
		snLayout.setAutoCreateGaps(true);
		snLayout.setAutoCreateContainerGaps(true);

		maxDistSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
		maxDistSpinner
				.setToolTipText("<html>Maximum distance to consider any Virtual node as a candidate</html>");
		JLabel maxDistLabel = new JLabel("Maximum distance");

		kSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
		kSpinner.setToolTipText("<html>Number of shortest paths</html>");
		JLabel kLabel = new JLabel("Number of k");

		yesIsDistanceButton = new JRadioButton(yesIsDistanceString, false);
		yesIsDistanceButton.setMnemonic(KeyEvent.VK_D);
		yesIsDistanceButton.setActionCommand(yesIsDistanceString);
		yesIsDistanceButton.addActionListener(this);
		JLabel isDistanceLabel = new JLabel("Is distance considered?");

		noIsDistanceButton = new JRadioButton(noIsDistanceString, false);
		noIsDistanceButton.setMnemonic(KeyEvent.VK_R);
		noIsDistanceButton.setActionCommand(noIsDistanceString);
		noIsDistanceButton.addActionListener(this);

		// Group the radio buttons.
		ButtonGroup isDistancegroup = new ButtonGroup();
		isDistancegroup.add(yesIsDistanceButton);
		isDistancegroup.add(noIsDistanceButton);

		yesNodeOverloadButton = new JRadioButton(yesNodeOverloadString);
		yesNodeOverloadButton.setMnemonic(KeyEvent.VK_D);
		JLabel nodeOverloadLabel = new JLabel("Is node overload considered?");

		noNodeOverloadButton = new JRadioButton(noNodeOverloadString);
		noNodeOverloadButton.setMnemonic(KeyEvent.VK_R);

		// Group the radio buttons.
		ButtonGroup nodeOverload = new ButtonGroup();
		nodeOverload.add(yesNodeOverloadButton);
		nodeOverload.add(noNodeOverloadButton);

		SequentialGroup snHorizontal = snLayout.createSequentialGroup();
		snLayout.setHorizontalGroup(snHorizontal);
		snHorizontal.addGroup(snLayout.createParallelGroup()
				.addComponent(kLabel).addComponent(isDistanceLabel)
				.addComponent(maxDistLabel).addComponent(nodeOverloadLabel));
		snHorizontal.addGroup(snLayout
				.createParallelGroup()
				.addComponent(kSpinner, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(yesIsDistanceButton, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(noIsDistanceButton, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(maxDistSpinner, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(yesNodeOverloadButton,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addComponent(noNodeOverloadButton, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));

		SequentialGroup snVertical = snLayout.createSequentialGroup();
		snLayout.setVerticalGroup(snVertical);

		snVertical.addGroup(snLayout
				.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(kLabel)
				.addComponent(kSpinner, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));

		snVertical.addGroup(snLayout
				.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(isDistanceLabel)
				.addComponent(yesIsDistanceButton, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));

		snVertical.addGroup(snLayout
				.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(isDistanceLabel)
				.addComponent(noIsDistanceButton, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));

		snVertical.addGroup(snLayout
				.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(maxDistLabel).addComponent(maxDistSpinner));

		snVertical.addGroup(snLayout
				.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(nodeOverloadLabel)
				.addComponent(yesNodeOverloadButton,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE));

		snVertical.addGroup(snLayout
				.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(nodeOverloadLabel)
				.addComponent(noNodeOverloadButton, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));

		// add the components to the content panel
		col.addComponent(parPanel);
		row.addComponent(parPanel);

		return content;
	}

	@Override
	protected void doAction() {

		if (!yesIsDistanceButton.isSelected()
				&& !noIsDistanceButton.isSelected()) {
			JOptionPane
					.showMessageDialog(GUI.getInstance(),
							"<html><p><b>'Distance'</b> parameter must be specified</p><html>");
			return;
		} else if (!yesNodeOverloadButton.isSelected()
				&& !noNodeOverloadButton.isSelected()) {
			JOptionPane
					.showMessageDialog(GUI.getInstance(),
							"<html><p><b>'Node Overload'</b> parameter must be Yes or No</p><html>");
			return;
		}
		
		// Algorithm parameters
		Scenario scenario = GUI.getInstance().getScenario();
		NetworkStack ns = scenario.getNetworkStack();
		AlgorithmParameter param = new AlgorithmParameter();
		param.put("PathSplitting", "False");
		String distance = noIsDistanceButton.isSelected() ? "-1" : maxDistSpinner.getValue().toString();
		param.put("distance", distance);
		param.put("kShortestPaths", kSpinner.getValue().toString());
		String overload = yesNodeOverloadButton.isSelected() ? "True" : "False";
		param.put("overload", overload);

		// finally generate the algorithm
		AbstractAlgorithm algo = new AvailableResources(param);
		algo.setStack(ns);
		
		new MyProgressBarDialog(algo);
		GUI.getInstance().getGraphPanel().autoZoomToFit();
	}

}
