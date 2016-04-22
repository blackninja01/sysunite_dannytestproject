import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.shell.impl.SystemOutput;

import java.util.Scanner;

public class Main {

    //static final String DB_PATH = "Users\\Danny\\Documents\\Neo4j\\default.graphdb";

    public static final String DB_PATH = "Neo4j\\default.graphdb";

    public static final int DOJODENHAAGNUM = 5;
    public static final int DOJOROTTERDAMNUM = 6;
    public static final int DOJOAMSTERDAMNUM = 7;
    public static final int DOJOHAARLEMNUM = 8;
    public static final int DOJOALPHENAANDERIJNNUM = 11;
    public static final int DOJOAMERSFOORTNUM = 12;
    public static final int DOJOARNHEMNUM = 13;
    public static final int DOJOUTRECHTNUM = 14;
    public static final int DOJOUTRECHTLUNETTENNUM = 15;
    public static final int DOJOOEGSTGEESTNUM = 19;



    public Node first;
    public Node second;
    public Relationship relation;
    public GraphDatabaseService graphDataService;

    public Label style = DynamicLabel.label("Style");
    public Label dojo = DynamicLabel.label("Dojo");
    public Label student = DynamicLabel.label("Student");

    public String DenHaag = "Rotterdam";

    Scanner userInput = new Scanner(System.in);


    public enum RelationType implements RelationshipType {
        Taught, Teaches, Trains,
    }

    public enum NodeType implements Label{
        Person, Course, Style
    }



    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Main main = new Main();

        //main.createDatabase();
        //main.welcomeUser();
        main.query();
        main.findStudent();
        main.showStudentsFromDojo();
        /*main.createNewStudent();
        main.readDatabase();
        main.removeDatabase();
        main.shutDown();*/
    }

    void welcomeUser(){
        System.out.println("Welcome user");
        System.out.println("Please select a new action:");
        System.out.println("1. Enter a new Student");
        System.out.println("2. Shutdown");

        String userInputValue = userInput.nextLine();

        if(userInputValue.equals("1")){
            createNewStudent();
        }else if (userInputValue.equals("2")){
            shutDown();
        }else {
            mainMenu();
        }
    }

    void mainMenu(){
        System.out.println("Please select a new action:");
        System.out.println("1. Enter a new Student");
        System.out.println("2. Shutdown");

        userInput.next();

        if(userInput.toString() == "1"){
            createNewStudent();
        }else if (userInput.toString() == "2"){
            shutDown();
        }
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

            //graphDataService.findNodes(dojo);

           // relation = first.createRelationshipTo(graphDataService.get(dojo, DenHaag, graphDataService), RelationType.Taught);
           // relation.setProperty("relationship-type", "shares");

            // succes transaction
            transaction.success();

        }
        finally {
            transaction.finish();
        }

        mainMenu();

    }


    void createNewStudent(){

        graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        int studentTrainingLocation = 0;

        System.out.println("What is new students first name?");
        String studentFirstName = userInput.next();

        System.out.println("What is new students last name?");
        String studentLastName = userInput.next();

        System.out.println("Where does the student train?");
        System.out.println("1. Amsterdam");
        System.out.println("2. Amersfoort");
        System.out.println("3. Alphen aan de Rijn");
        System.out.println("4. Arnhem");
        System.out.println("5. Den Haag");
        System.out.println("6. Haarlem");
        System.out.println("7. Rotterdam");
        System.out.println("8. Utrecht");
        System.out.println("9. Utrecht Lunetten");
        System.out.println("10. Oegstgeest");
        System.out.println("11. Unlimited Contract");
        String studentTrainingLoc = userInput.next();
        if (studentTrainingLoc.equals("1")){
            studentTrainingLocation = 7;
        } else if (studentTrainingLoc.equals("2")){
            studentTrainingLocation = 12;
        } else if (studentTrainingLoc.equals("3")){
            studentTrainingLocation = 11;
        } else if (studentTrainingLoc.equals("4")){
            studentTrainingLocation = 13;
        } else if (studentTrainingLoc.equals("5")){
            studentTrainingLocation = 5;
        } else if (studentTrainingLoc.equals("6")){
            studentTrainingLocation = 8;
        } else if (studentTrainingLoc.equals("7")){
            studentTrainingLocation = 6;
        } else if (studentTrainingLoc.equals("8")){
            studentTrainingLocation = 14;
        } else if (studentTrainingLoc.equals("9")){
            studentTrainingLocation = 15;
        } else if (studentTrainingLoc.equals("10")){
            studentTrainingLocation = 19;
        }

        Transaction transaction = graphDataService.beginTx();
        if (!(studentTrainingLoc.equals("11"))) {
            try {
                first = graphDataService.createNode();
                first.addLabel(student);
                first.setProperty("Voornaam", studentFirstName);
                first.setProperty("Achternaam", studentLastName);

                second = graphDataService.getNodeById(studentTrainingLocation);

                first.createRelationshipTo(second, RelationType.Trains);

                transaction.success();


            } finally {
                transaction.finish();
            }
        } else{
            try {
                first = graphDataService.createNode();
                first.addLabel(student);
                first.setProperty("Voornaam", studentFirstName);
                first.setProperty("Achternaam", studentLastName);

                second = graphDataService.getNodeById(5);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphDataService.getNodeById(6);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphDataService.getNodeById(7);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphDataService.getNodeById(8);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphDataService.getNodeById(11);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphDataService.getNodeById(12);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphDataService.getNodeById(13);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphDataService.getNodeById(14);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphDataService.getNodeById(15);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphDataService.getNodeById(19);
                first.createRelationshipTo(second, RelationType.Trains);

                transaction.success();


            } finally {
                transaction.finish();
            }
        }
        mainMenu();
    }


    void readDatabase()
    {
    }

    void query(){
        GraphDatabaseService graphService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        Transaction ignored = graphService.beginTx();
        try {

            Result result = graphService.execute( "match (n:Dojo) return ID(n), n.Location" );
            while (result.hasNext() ){
                String PossibleLocations = result.next().toString();
                PossibleLocations = PossibleLocations.replaceAll("n.Location=", "");
                PossibleLocations = PossibleLocations.replaceAll("[(){}=]", "");
                PossibleLocations = PossibleLocations.replaceAll("IDn", "");
                PossibleLocations = PossibleLocations.replaceAll(",", ".");
                System.out.println(PossibleLocations);
            }
            ignored.success();

        }finally {
            ignored.finish();
            graphService.shutdown();
        }
    }

    void findStudent(){
        GraphDatabaseService graphService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        Transaction ignored = graphService.beginTx();

        String FirstName = "Super";
        String LastName = "Man";


        try {

            Result result = graphService.execute( "match (n) where n.Voornaam = '" +FirstName +"' and n.Achternaam = '"+LastName+"' return (n)");
            while (result.hasNext()){
                System.out.println(result.next());
            }
            ignored.success();
        } finally {
            ignored.finish();
            graphService.shutdown();
        }
    }

    void showStudentsFromDojo(){
        GraphDatabaseService graphService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        Transaction ignored = graphService.beginTx();

        String DojoLocation = "Haarlem";


        try {
            Result result = graphService.execute( "MATCH (Dojo { Location:'"+DojoLocation+"' })<-[:Trains]-(Student) return Student.Voornaam, Student.Achternaam");
            while (result.hasNext()){
                String StudentenNaam = result.next().toString();
                StudentenNaam = StudentenNaam.replaceAll("Student.Voornaam=", "");
                StudentenNaam = StudentenNaam.replaceAll("Student.Achternaam=", "");
                StudentenNaam = StudentenNaam.replaceAll("[{},]", "");
                System.out.println(StudentenNaam);
            }
            ignored.success();
        } finally {
            ignored.finish();
            graphService.shutdown();
        }
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
