package ppimapbuilder.network;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ppimapbuilder.ppidb.api.DBConnector;
import ppimapbuilder.ppidb.api.SQLResult;

public class NetworkAbstraction {
	
	public static SQLResult getAllData(String id, ArrayList<String> dbList, ArrayList<Integer> orgaList) {
		SQLResult res;
		// Get result from 
		try {
			res = DBConnector.Instance().getAllData(id, dbList, orgaList);
			if(res.isEmpty()) 
				JOptionPane.showMessageDialog(null, id+" not found");
		} catch (Exception e1) {
			//JOptionPane.showMessageDialog(null, "Error SQL : "+e1.getLocalizedMessage());
			return null;
		}
		return res;
	}
	
}
