package ppimapbuilder.ppidb.api;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 *
 * @author CORNUT, CRESSANT, DUPUIS, GRAVOUIL
 *
 */
public class DBConnector {

    private static DBConnector _instance = null; // Instance of the dbconnnector to prevent several instances 
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private String url;
    private String user;
    private String password;

    /**
     * Default constructor
     */
    private DBConnector() throws SQLException, IOException {
        this.getServerConfig();
        con = DriverManager.getConnection(this.url, this.user, this.password);
        pst = con.prepareStatement(
                " SELECT DISTINCT"
                + "     interaction.id AS \"id\","
                + "     p1.gene_name AS \"interactor_nameA\","
                + "     p1.uniprot_id AS \"uniprotA\","
                + "     p2.gene_name AS \"interactor_nameB\","
                + "     p2.uniprot_id AS \"uniprotB\","
                + "     org.tax_id AS \"taxID\","
                + "     db.name AS \"db_source\","
                + "     expsys.name AS \"exp_system\","
                + "     pub.pubmed_id as \"pubmed_id\""
                + " FROM link_data_interaction"
                + "     JOIN interaction_data ON link_data_interaction.interaction_id = interaction_data.id"
                + "     JOIN source_database AS \"db\" ON interaction_data.db_source_name = db.name"
                + "     JOIN organism AS \"org\" ON interaction_data.organism_tax_id = org.tax_id"
                + "     JOIN experimental_system AS \"expsys\" ON interaction_data.experimental_system = expsys.name"
                + "     JOIN publication AS \"pub\" ON interaction_data.pubmed_id = pub.pubmed_id"
                + "     JOIN interaction ON link_data_interaction.interaction_id = interaction.id"
                + "     JOIN protein AS \"p1\" ON interaction.protein_id1 = p1.id"
                + "     JOIN protein AS \"p2\" ON interaction.protein_id2 = p2.id"
                + " WHERE p1.uniprot_id = ?");
    }

    private void getServerConfig() throws IOException {
        // Open the file that is the first command line parameter
        // & Get the object of DataInputStream
        BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream("server.cfg"))));
        
        this.url = br.readLine();
        this.user = br.readLine();
        this.password = br.readLine();

        //Close the input stream
        br.close();
    }

    /**
     * Method to access the unique instance of DBconnector
     *
     * @return _instance
     */
    public static DBConnector Instance() throws SQLException, IOException {
        if (_instance == null) {
            _instance = new DBConnector();
        }
        return _instance;
    }

    /**
     * Give a HashMap of available organisms in the PPiMapBuilder database.
     *
     * @return HashMap<OrganismName, TaxID>
     * @throws SQLExeception
     */
    public LinkedHashMap<String, Integer> getOrganisms() throws SQLException {
        LinkedHashMap<String, Integer> orga = new LinkedHashMap<String, Integer>();
        ResultSet rs = this.con.createStatement().executeQuery("SELECT tax_id AS \"id\", name AS \"organism\" FROM organism");
        while (rs.next()) {
            orga.put(rs.getString("organism"), rs.getInt("id"));
        }

        return orga;
    }

    /**
     * Give a list of reference databases available in the PPiMapBuilder
     * database.
     *
     * @return ArrayList<String>
     * @throws SQLExeception
     */
    public ArrayList<String> getDatabases() throws SQLException {
        ArrayList<String> db = new ArrayList<String>();
        ResultSet rs = this.con.createStatement().executeQuery("SELECT initcap(name) as \"db\" FROM source_database");
        while (rs.next()) {
            db.add(rs.getString("db"));
        }
        return db;
    }

    /**
     * Get data about a protein identified by its UniprotID.
     *
     * @param uniprot UniprotID
     * @return SQLResult
     * @throws SQLExeception
     */
    public SQLResult getAllData(String uniprot) throws SQLException {
        pst.setString(1, uniprot);
        rs = pst.executeQuery();
        return new SQLResult(rs);
    }

    public Set<String> getKeys(SQLResult sqlr) {
        return sqlr.keySet();
    }

    /**
     * Get data about a list of proteins identified by their UniprotID.
     *
     * @param uniprot UniprotID
     * @return SQLResult
     * @throws SQLExeception
     */
    public SQLResult getAllData(ArrayList<String> uniprotList) throws SQLException {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {
            System.err.println("Error while disconnecting : " + ex.getLocalizedMessage());
        }
    }
}
