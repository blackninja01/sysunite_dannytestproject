import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import scala.util.control.Exception;

public class Main {

    //static final String DB_PATH = "Users\\Danny\\Documents\\Neo4j\\default.graphdb";

    public static final String DB_PATH = "Neo4j\\default.graphdb";

    public Node first;
    public Node second;
    public Relationship relation;
    public GraphDatabaseService graphDataService;

    public Label style = DynamicLabel.label("Style");
    public Label dojo = DynamicLabel.label("Dojo");
    public Label student = DynamicLabel.label("Student");

    public String DenHaag = "Rotterdam";


    public enum RelationType implements RelationshipType {
        Taught, Teaches, Trains,
    }

    public enum NodeType implements Label{
        Person, Course, Style
    }



    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Main main = new Main();

        main.createDatabase();
        main.readDatabase();
        main.removeDatabase();
        main.shutDown();
    }

    void createDatabase()
    {
        //GraphDatabaseService
        graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);


        //Begin Transaction
        Transaction transaction = graphDataService.beginTx();
        try {
            first = graphDataService.createNode();
            first.addLabel(style);
            first.setProperty("Style", "Krav Maga");

            second = graphDataService.createNode();
            second.addLabel(style);
            second.setProperty("Style", "Jujitsu");

            graphDataService.findNodes(dojo);

           // relation = first.createRelationshipTo(graphDataService.get(dojo, DenHaag, graphDataService), RelationType.Taught);
            relation.setProperty("relationship-type", "shares");

            // succes transaction
            transaction.success();

        }
        finally {
            transaction.finish();
        }

    }


    void createNewStudent(){
        Transaction transaction = graphDataService.beginTx();

        try {
            first = graphDataService.createNode();
            first.addLabel(student);
            first.setProperty("Voornaam", "Bob");
            first.setProperty("Achternaam", "Bobbinson");


        }finally {

        }
    }


    void readDatabase()
    {
    }

    void removeDatabase()
    {
        Transaction transaction = graphDataService.beginTx();

        try {
            first.delete();
            second.delete();
        }
        finally {
            transaction.finish();
        }

    }
    void shutDown()
    {
        graphDataService.shutdown();
    }

}
