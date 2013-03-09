package ppimapbuilder.gui;

import javax.swing.*;

import ppimapbuilder.gui.listener.CreateNetworkMenuAction;
import ppimapbuilder.gui.listener.CreditsMenuAction;

/**
 * 
 * @author CORNUT, CRESSANT, DUPUIS, GRAVOUIL
 *
 */
public class PMBMenu extends JMenu{

	private static final long serialVersionUID = 1L;
	
	private static PMBMenu _instance = null; // Instance of the ppimapbuilder menu to prevent several instances 
	
	private JMenuItem menuItem_create_network, menuItem_credits; // Menu item to create a new network and to display the credits
	private CreateNetworkMenuAction myCreateNetworkAction; // Action to create a network
	private CreditsMenuAction myCreditsAction; // Action to display the credit
	
	/**
	 * Default constructor which is private to prevent several instances
	 * Create the main menu and add the differents menu item
	 */
	private PMBMenu() {
		super("PPiMapBuilder"); // Creates the ppimapbuilder menu as a JMenu and change the name of the plugin menu

		menuItem_create_network = new JMenuItem("Create a new network"); // Create the menu item corresponding to the create network action
		myCreateNetworkAction = new CreateNetworkMenuAction(); // Create the action
		menuItem_create_network.addActionListener(myCreateNetworkAction); // Link this action to the menu item
		
		menuItem_credits = new JMenuItem("About PPiMapBuilder"); // Create the menu item corresponding to the credits display
		myCreditsAction = new CreditsMenuAction(); // Create the action
		menuItem_credits.addActionListener(myCreditsAction); // Link this action to the menu item
		
		this.add(menuItem_create_network); // Add the menu item to the main menu
		this.add(menuItem_credits);
	}
	
	/**
	 * Method to access the unique instance of PMBMenu
	 * @return _instance
	 */
	public static PMBMenu Instance() {
		if (_instance == null)
			_instance = new PMBMenu();
		return _instance;
	}

}
