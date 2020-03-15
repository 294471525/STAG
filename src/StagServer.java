import java.io.*;
import java.net.*;
import java.util.*;

class StagServer
{
    private ArrayList<String> arr = new ArrayList<>();
    private HashMap<String, Player> players = new HashMap<>();
    private HashSet<Action> actions = new HashSet<>();
    private HashMap<String, Location> locations = new HashMap<>();
    private DOT dotParser = new DOT();
    private JSON josnParser = new JSON();
    public static void main(String args[])
    {
        if(args.length != 2) System.out.println("Usage: java StagServer <entity-file> <action-file>");
        else new StagServer(args[0], args[1], 8888);
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber)
    {
        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            actions = josnParser.parse("actions1.json");
            locations = dotParser.parse("entities1.dot");
            while(true) acceptNextConnection(ss);
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void acceptNextConnection(ServerSocket ss)
    {
        try {
            // Next line will block until a connection is received
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in, out);
            out.close();
            in.close();
            socket.close();
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out) throws IOException
    {
        String line = in.readLine();
        String playerName = line.substring(0,line.indexOf(':'));
        String[] commands = line.substring(line.indexOf(':')+2).split("\\s+");
        CommandProcessor processor = new CommandProcessor();
        if(processor.CheckIfPlayerExist(players, playerName)){
            Player newPlayer = new Player(null, playerName, null);
            processor.PlayerInitialize(newPlayer,locations.get("cabin"));
            players.put(playerName, newPlayer);
        }
        out.write("You said... " + line + "\n");
        processor.ProcessCommand(out, locations, actions, players.get(playerName),commands);
       // line = players.get(playerName).getLocation().getName();

    }
}
