import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.util.ArrayList;
import java.util.List;
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

    List<Integer> DojoNumbers = new ArrayList<Integer>();

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
        main.welcomeUser();
        //main.listAllDojos();
        //main.findStudent();
        //main.showStudentsFromDojo();
        /*main.createNewStudent();
        main.readDatabase();
        main.removeDatabase();
        main.shutDown();*/
    }

    void welcomeUser(){
        System.out.println("Welcome user");
        mainMenu();
    }

    void mainMenu(){
        System.out.println("Please select a new action:");
        System.out.println("1. Enter a new student");
        System.out.println("2. Show all info for a student");
        System.out.println("3. Show all students in a Dojo");
        System.out.println("4. Show all Dojo's");
        System.out.println("5. Shutdown");

        String MenuChoice = userInput.next();

        if(MenuChoice.equals("1")){
            createNewStudent();
        }else if (MenuChoice.equals("2")){
            findStudent();
        }else if (MenuChoice.equals("3")){
            showStudentsFromDojo();
        }else if (MenuChoice.equals("4")){
            listAllDojos();
        }else if (MenuChoice.equals("5")){
            shutDown();
        }else mainMenu();

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

    void listAllDojos(){
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
        mainMenu();
    }

    void findStudent(){
        GraphDatabaseService graphService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        Transaction ignored = graphService.beginTx();

        System.out.println("What is students first name?");
        String studentFirstName = userInput.next();

        System.out.println("What is students last name?");
        String studentLastName = userInput.next();

        try {

            Result result = graphService.execute( "match (n) where n.Voornaam = '" +studentFirstName +"' and n.Achternaam = '"+studentLastName+"' return (n)");
            while (result.hasNext()){
                String StudentNummer;
                StudentNummer = result.next().toString();
                StudentNummer = StudentNummer.replaceAll("n=Node","");
                StudentNummer = StudentNummer.replaceAll("[{}]","");
                System.out.println("Student ID:" + StudentNummer);
            }

            result = graphService.execute( "match (n { Voornaam:'" +studentFirstName +"', Achternaam:'"+studentLastName+"'})-[Trains]->(r) return r.Location");
            while (result.hasNext()){
                String TrainingsLocation;
                TrainingsLocation = result.next().toString();
                TrainingsLocation = TrainingsLocation.replaceAll("r.Location=", "");
                TrainingsLocation = TrainingsLocation.replaceAll("[{}]", "");
                System.out.println(TrainingsLocation);
            }
            ignored.success();
        } finally {
            ignored.finish();
            graphService.shutdown();
        }
        mainMenu();
    }

    void showStudentsFromDojo(){
        GraphDatabaseService graphService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        Transaction ignored = graphService.beginTx();

        String DojoLocation = "";

        System.out.println("Please select a dojo");
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

        String DojoSelection = userInput.next();

        if (DojoSelection.equals("1")){
            DojoLocation = "Amsterdam";
        } else if (DojoSelection.equals("2")){
            DojoLocation = "Amersfoort";
        } else if (DojoSelection.equals("3")){
            DojoLocation = "Alphen aan de Rijn";
        } else if (DojoSelection.equals("4")){
            DojoLocation = "Arnhem";
        } else if (DojoSelection.equals("5")){
            DojoLocation = "Den Haag";
        } else if (DojoSelection.equals("6")){
            DojoLocation = "Haarlem";
        } else if (DojoSelection.equals("7")){
            DojoLocation = "Rotterdam";
        } else if (DojoSelection.equals("8")){
            DojoLocation = "Utrecht";
        } else if (DojoSelection.equals("9")){
            DojoLocation = "Utrecht Lunetten";
        } else if (DojoSelection.equals("10")){
            DojoLocation = "Oegstgeest";
        }

        try {
            Result result = graphService.execute( "match (n)-[r: Teaches]-> (Dojo {Location: '" + DojoLocation + "'}) return n.Name");
            while (result.hasNext()){
                String SenseiNaam = result.next().toString();
                SenseiNaam = SenseiNaam.replaceAll("n.Name=", "");
                SenseiNaam = SenseiNaam.replaceAll("[{}]", "");
                System.out.println("Sensei: " + SenseiNaam);
            }


            result = graphService.execute( "MATCH (Dojo { Location:'"+DojoLocation+"' })<-[:Trains]-(Student) return Student.Voornaam, Student.Achternaam");
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
        mainMenu();
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
