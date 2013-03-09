package ppimapbuilder;

import giny.model.RootGraph;
import cytoscape.CyNode;

/**
 * 
 * @author CORNUT, CRESSANT, DUPUIS, GRAVOUIL
 *
 */
public class PMBNode extends CyNode {
	
	/**
	 * Constructor which inherits from the CyNode class
	 * This class is necessary and is used by the other ppimapbuilder node constructor
	 * @param root
	 * @param rootGraphIndex
	 */
	public PMBNode(RootGraph root, int rootGraphIndex) {
		super(root, rootGraphIndex);
	}

	/**
	 * Constructor which creates a ppimapbuilder node directly from a CyNode
	 * @param myNode
	 */
	public PMBNode(CyNode myNode) {
		this(myNode.getRootGraph(), myNode.getRootGraphIndex());
	}
		

}