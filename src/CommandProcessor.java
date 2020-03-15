import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;


public class CommandProcessor {

    public boolean CheckIfPlayerExist(HashMap<String,Player> players, String playerName){
        return players.get(playerName) == null;
    }

    public void PlayerInitialize(Player newPlayer, Location start){
        newPlayer.setLocation(start);
    }

    public void ProcessCommand(BufferedWriter out, HashMap<String, Location> locations, HashSet<Action> actions, Player player, String[] commands) throws IOException {
        Location loc = player.getLocation();
        if(commands[0].equals("goto") && loc.getPathTo().contains(commands[1])){
            player.setLocation(locations.get(commands[1]));
            return;
        }

        if(commands[0].equals("inventory")){
            out.write("You have:" + "\n");
            for(Map.Entry<String, Artefact> entry : player.getArtefacts().entrySet()){
                out.write("     "+ entry.getValue().name + "\n");
            }
            return;
        }

        if(commands[0].equals("get") && loc.getArtefacts().get(commands[1])!= null ){
            player.addArtefact(loc.getArtefacts().get(commands[1]));
            loc.getArtefacts().remove(commands[1]);
            out.write("You get the "+commands[1]);
            return;
        }

        if (commands[0].equals("drop") && player.getArtefacts().get(commands[1]) != null) {
            player.getArtefacts().remove(commands[1]);
            out.write("You drop the "+commands[1]);
            return;
        }

        if(commands[0].equals("look")){
            out.write("Artefacts:"+"\n");
            for(Map.Entry<String, Artefact> entry : loc.getArtefacts().entrySet()){
                out.write(entry.getValue().name + "\n");
            }
            out.write("Furnitures:"+"\n");
            for(Map.Entry<String, Furniture> entry : loc.getFurnitures().entrySet()){
                out.write(entry.getValue().name + "\n");
            }
            out.write("Characters:"+"\n");
            for(Map.Entry<String, Character> entry : loc.getCharacters().entrySet()){
                out.write(entry.getValue().name + "\n");
            }
            out.write("Path:"+"\n");
            for(String path : loc.getPathTo()){
                out.write( player.getLocation().name + " to "+ path + "\n");
            }
            return;
        }
        for(Action act : actions){
            if(act.getTriggers().contains(commands[0])){
                boolean flag = true;
                HashSet<String> commandHashSet = new HashSet<>(Arrays.asList(commands));
                for(String sub : act.getSubjects()){
                    if(commandHashSet.contains(sub))
                    {
                        if(loc.name.equals(sub)){
                            continue;
                        }
                        else if(loc.getFurnitures().get(sub) == null){
                            continue;
                        }
                        else if(loc.getCharacters().get(sub) == null){
                            continue;
                        }
                        else if(loc.getArtefacts().get(sub) == null){
                            continue;
                        }
                        else if(player.getArtefact(sub) == null){
                            continue;
                        }
                    }
                    flag = false;
                }
                if(flag){
                    for(String con : act.getConsumed()){
                        if(player.getArtefact(con) == null){
                            flag = false;
                        }
                        if(loc.getFurnitures().get(con) != null){
                            flag = true;
                        }
                    }
                }
                if(flag){
                    for(String pro :act.getProduced()){
                        if( locations.get(pro) != null){
                            loc.addPathTo(pro);
                            //player.setLocation(locations.get(pro));
                        }
                        else {
                            Artefact newArtefact = new Artefact(null, pro, null);
                            player.addArtefact(newArtefact);
                        }
                    }
                    out.write(act.getNarration());
                }
            }
        }

    }

}
