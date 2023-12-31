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
package vnreal;

import java.util.LinkedList;
import java.util.Locale;

import javax.xml.bind.JAXBException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;


import vnreal.core.Orchestrator;
import vnreal.core.Scenario;
import vnreal.core.oldFramework.TestOrchestrator;
import vnreal.gui.GUI;
import vnreal.network.NetworkStack;
import vnreal.network.substrate.SubstrateNetwork;
import vnreal.network.virtual.VirtualNetwork;

public final class Main {

	public static void main(String[] args) {
		
		Options options = new Options();
		
		options.addOption("test", true, "Start headless experiment with the specified .test file");
		options.addOption("xmltest", true, "Start headless experiment with the specified .xmltest file");
		options.addOption("gui", false, "Start the GUI for manual experimentation");
		
		CommandLine cline = null;
		try {
			cline = new BasicParser().parse(options, args);
		} catch (ParseException e) {
			System.out.println("Problem while parsing command line arguments:");
			e.printStackTrace();
		}
		
		if (cline.hasOption("test")) {
			TestOrchestrator.execute(cline.getOptionValue("test"));
			
		} else if (cline.hasOption("xmltest")) {
			try {
				Orchestrator.execute(cline.getOptionValue("xmltest"));
			} catch (ClassNotFoundException | SAXException | JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			// Schedule a job for the event-dispatching thread:
			// creating and showing this application's GUI.
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					Locale.setDefault(Locale.US);
	
					Scenario scenario = new Scenario();
					scenario.setNetworkStack(new NetworkStack(new SubstrateNetwork(true), new LinkedList<VirtualNetwork>()));
					new GUI(scenario);
				}
			});
		}
	}
}
