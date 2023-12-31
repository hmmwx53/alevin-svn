package vnreal.algorithms.DPVNE.singlenetworkmapping;

import java.util.LinkedList;
import java.util.List;

import vnreal.algorithms.AlgorithmParameter;
import vnreal.algorithms.AvailableResources;
import vnreal.algorithms.GenericMappingAlgorithm;
import vnreal.algorithms.singlenetworkmapping.SingleNetworkMappingAlgorithm;
import vnreal.evaluations.utils.VnrUtils;
import vnreal.hiddenhopmapping.BandwidthCpuHiddenHopMapping;
import vnreal.hiddenhopmapping.IHiddenHopMapping;
import vnreal.network.NetworkStack;
import vnreal.network.substrate.SubstrateNetwork;
import vnreal.network.virtual.VirtualNetwork;

public class RWMMSP extends SingleNetworkMappingAlgorithm {

	public RWMMSP() {
		super(false);
	}

	@Override
	public boolean mapNetwork(SubstrateNetwork network, VirtualNetwork vNetwork) {
		List<VirtualNetwork> vns = new LinkedList<VirtualNetwork>();
		vns.add(vNetwork);
		NetworkStack stack = new NetworkStack(network, vns);
		LinkedList<IHiddenHopMapping> hhMappings = new LinkedList<IHiddenHopMapping>();
		double hiddenHopsFactor = 0;
		hhMappings.add(new BandwidthCpuHiddenHopMapping(hiddenHopsFactor));

		AlgorithmParameter param = new AlgorithmParameter();
		param.put("kShortestPath", "50");
		param.put("distance", "35");
		param.put("PathSplitting", "false");
		param.put("eppstein", "false");
		GenericMappingAlgorithm algo = new AvailableResources(param);
		algo.setStack(stack);
		if (algo instanceof GenericMappingAlgorithm)
			((GenericMappingAlgorithm) algo).setHhMappings(hhMappings);

		algo.performEvaluation();
		return VnrUtils.isMapped(vNetwork);

	}

}