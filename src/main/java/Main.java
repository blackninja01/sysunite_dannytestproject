import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.lang.Thread;

public class Main{
    static final String DB_PATH = "Neo4j\\default.graphdb";
    static final int SERVERPORT = 23;

    Node first;
    Node second;
    GraphDatabaseService graphDataService;

    Label student = DynamicLabel.label("Student");
    boolean notified = false;


    enum RelationType implements RelationshipType {
        Taught, Teaches, Trains,
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();

        while (true) {
            main.serverHandeling();
        }
    }

    void serverHandeling(){
        try {
            ServerSocket serverSocket = new ServerSocket(SERVERPORT);
            notified = false;
            System.out.println("Server started!\r\n" +
                    "Servers IP-address: " + InetAddress.getLocalHost().getHostAddress() + "\r\n" +
                    "Servers Listening Port: " + SERVERPORT + "\r\n");

            Socket connectionSocket = serverSocket.accept(); //Make a connection



                BufferedReader messagesFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); //Read what is incomming
                PrintWriter outgoingMessage = new PrintWriter(connectionSocket.getOutputStream(), true); // prepare for sending
                boolean MainMenuIsNeeded = true;


                while (true) {
                    if (MainMenuIsNeeded) {
                        outgoingMessage.println("Please select a new action:\r\n" +
                                "1. Enter a new student\r\n" +
                                "2. Show all info for a student\r\n" +
                                "3. Show all students in a Dojo\r\n" +
                                "4. Show all Dojo's\r\n" +
                                "5. Shutdown");
                    }
                    MainMenuIsNeeded = true;

                    String clientCommand = messagesFromClient.readLine();
                    String connectwith = connectionSocket.getInetAddress().toString();
                    connectwith = connectwith.replaceAll("/", "");
                    System.out.println("Connected with: " + connectwith);
                    System.out.println("Received: " + clientCommand);


                    String MenuChoice = clientCommand;

                    if (MenuChoice.equals("1")) {
                        try {
                            createNewStudentforClient(serverSocket, connectionSocket, outgoingMessage, messagesFromClient);
                        } catch (Exception ee) {
                        }
                        MainMenuIsNeeded = false;
                    } else if (MenuChoice.equals("2")) {
                        try {
                            findStudentforClient(serverSocket, connectionSocket, outgoingMessage, messagesFromClient);
                        } catch (Exception ee) {
                        }
                        MainMenuIsNeeded = false;
                    } else if (MenuChoice.equals("3")) {
                        try {
                            showStudentsFromDojoforClient(serverSocket, connectionSocket, outgoingMessage, messagesFromClient);
                        } catch (Exception ee) {
                        }
                        MainMenuIsNeeded = false;
                    } else if (MenuChoice.equals("4")) {
                        try {
                            listAllDojosforClient(serverSocket, connectionSocket, outgoingMessage);
                        } catch (Exception ee) {
                        }
                        MainMenuIsNeeded = false;
                    } else if (MenuChoice.equals("5")) {
                        shutDown();
                    }
                }

        } catch (Exception eeo) {
            if (!(notified)){
                System.out.println("We lost a connection");
                notified = true;
            }
        }
    }

    void listAllDojosforClient(ServerSocket serverSocket, Socket connectionSocket, PrintWriter outgoingMessage) {
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
                outgoingMessage.println(PossibleLocations);
            }
            ignored.success();

        }finally {
            ignored.finish();
            graphService.shutdown();
        }
    }
    void createNewStudentforClient(ServerSocket serverSocket, Socket connectionSocket, PrintWriter outgoingMessage, BufferedReader messagesFromClient) throws Exception{

        GraphDatabaseService graphService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);

        //graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        int studentTrainingLocation = 0;

        outgoingMessage.println("What is new students first name?");
        String studentFirstName = messagesFromClient.readLine();

        outgoingMessage.println("What is new students last name?");
        String studentLastName = messagesFromClient.readLine();

        outgoingMessage.println("Where does the student train? \r\n" +
                                "1. Amsterdam \r\n" +
                                "2. Amersfoort \r\n" +
                                "3. Alphen aan de Rijn \r\n" +
                                "4. Arnhem \r\n" +
                                "5. Den Haag \r\n" +
                                "6. Haarlem \r\n" +
                                "7. Rotterdam \r\n" +
                                "8. Utrecht \r\n" +
                                "9. Utrecht Lunetten \r\n" +
                                "10. Oegstgeest \r\n" +
                                "11. Unlimited Contract");

        String studentTrainingLoc = messagesFromClient.readLine();

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

        Transaction transaction = graphService.beginTx();
        if (!(studentTrainingLoc.equals("11"))) {
            try {
                first = graphService.createNode();
                first.addLabel(student);
                first.setProperty("Voornaam", studentFirstName);
                first.setProperty("Achternaam", studentLastName);

                second = graphService.getNodeById(studentTrainingLocation);

                first.createRelationshipTo(second, RelationType.Trains);

                transaction.success();


            } finally {
                transaction.finish();
                graphService.shutdown();
            }
        } else{
            try {
                first = graphService.createNode();
                first.addLabel(student);
                first.setProperty("Voornaam", studentFirstName);
                first.setProperty("Achternaam", studentLastName);

                second = graphService.getNodeById(5);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphService.getNodeById(6);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphService.getNodeById(7);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphService.getNodeById(8);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphService.getNodeById(11);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphService.getNodeById(12);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphService.getNodeById(13);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphService.getNodeById(14);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphService.getNodeById(15);
                first.createRelationshipTo(second, RelationType.Trains);
                second = graphService.getNodeById(19);
                first.createRelationshipTo(second, RelationType.Trains);

                transaction.success();


            } finally {
                transaction.finish();
                graphService.shutdown();
            }
        }
    }

    void findStudentforClient(ServerSocket serverSocket, Socket connectionSocket, PrintWriter outgoingMessage, BufferedReader messagesFromClient) throws Exception{

        GraphDatabaseService graphService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        Transaction ignored = graphService.beginTx();

        outgoingMessage.println("What is students first name?");
        String studentFirstName =  messagesFromClient.readLine();

        outgoingMessage.println("What is students last name?");
        String studentLastName =  messagesFromClient.readLine();

        try {

            Result result = graphService.execute( "match (n) where n.Voornaam = '" +studentFirstName +"' and n.Achternaam = '"+studentLastName+"' return (n)");
            while (result.hasNext()){
                String StudentNummer;
                StudentNummer = result.next().toString();
                StudentNummer = StudentNummer.replaceAll("n=Node","");
                StudentNummer = StudentNummer.replaceAll("[{}]","");
                outgoingMessage.println("Student ID:" + StudentNummer);
            }

            result = graphService.execute( "match (n { Voornaam:'" +studentFirstName +"', Achternaam:'"+studentLastName+"'})-[Trains]->(r) return r.Location");
            while (result.hasNext()){
                String TrainingsLocation;
                TrainingsLocation = result.next().toString();
                TrainingsLocation = TrainingsLocation.replaceAll("r.Location=", "");
                TrainingsLocation = TrainingsLocation.replaceAll("[{}]", "");
                outgoingMessage.println(TrainingsLocation);
            }
            ignored.success();
        } finally {
            ignored.finish();
            graphService.shutdown();
        }
    }

    void showStudentsFromDojoforClient(ServerSocket serverSocket, Socket connectionSocket, PrintWriter outgoingMessage, BufferedReader messagesFromClient) throws Exception{
        GraphDatabaseService graphService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        Transaction ignored = graphService.beginTx();

        String DojoLocation = "";

        outgoingMessage.println("Please select a dojo \r\n" +
                                "1. Amsterdam \r\n" +
                                "2. Amersfoort \r\n" +
                                "3. Alphen aan de Rijn \r\n" +
                                "4. Arnhem \r\n" +
                                "5. Den Haag \r\n" +
                                "6. Haarlem \r\n" +
                                "7. Rotterdam \r\n" +
                                "8. Utrecht \r\n" +
                                "9. Utrecht Lunetten \r\n" +
                                "10. Oegstgeest \r\n");

        String DojoSelection = messagesFromClient.readLine();

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
                outgoingMessage.println("Sensei: " + SenseiNaam);
            }


            result = graphService.execute( "MATCH (Dojo { Location:'"+DojoLocation+"' })<-[:Trains]-(Student) return Student.Voornaam, Student.Achternaam");
            while (result.hasNext()){
                String StudentenNaam = result.next().toString();
                StudentenNaam = StudentenNaam.replaceAll("Student.Voornaam=", "");
                StudentenNaam = StudentenNaam.replaceAll("Student.Achternaam=", "");
                StudentenNaam = StudentenNaam.replaceAll("[{},]", "");
                outgoingMessage.println(StudentenNaam);
            }


            ignored.success();
        } finally {
            ignored.finish();
            graphService.shutdown();
        }
    }

    void shutDown()
    {
        graphDataService.shutdown();
    }

}
