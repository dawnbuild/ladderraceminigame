package dawn.eventminigame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Area {
    Location loc1;
    Location loc2;
    public Area(Location loc1, Location loc2){
        this.loc2=loc2;
        this.loc1=loc1;
    }
    public boolean isPlayerInsideArea(Player player){
        Location playerLoc = player.getLocation();
        return playerLoc.getX() >= loc1.getX() && playerLoc.getY() >= loc1.getY() && playerLoc.getZ() >= loc1.getZ()
                && playerLoc.getX() <= loc2.getX() && playerLoc.getY() <= loc2.getY() && playerLoc.getZ() <= loc2.getZ();
    }

    public List<Block> getBlocks(){
        int x1 = (int) loc1.getX();
        int y1 = (int) loc1.getY();
        int z1 = (int) loc1.getZ();
        int x2 = (int) loc2.getX();
        int y2 = (int) loc2.getY();
        int z2 = (int) loc2.getZ();
        List<Block> blocks = new ArrayList<>();
        for (int i = x1; i<=x2;i++){
            for (int j = y1; j<=y2;j++){
                for (int k = z1; k<=z2;k++){
                    Location loc = new Location(loc1.getWorld(),i,j,k);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }

    public static void save(String path,Location loc1,Location loc2){
        int x1 = (int) loc1.getX();
        int y1 = (int) loc1.getY();
        int z1 = (int) loc1.getZ();
        int x2 = (int) loc2.getX();
        int y2 = (int) loc2.getY();
        int z2 = (int) loc2.getZ();
        int temp;
        if (x1>x2){
            temp=x1;
            x1=x2;
            x2=temp;
        }if (y1>y2){
            temp=y1;
            y1=y2;
            y2=temp;
        }if (z1>z2){
            temp=z1;
            z1=z2;
            z2=temp;
        }

        loc1 = new Location(loc1.getWorld(), x1,y1,z1);
        loc2 = new Location(loc2.getWorld(), x2,y2,z2);

        Eventminigame.getInstance().getConfig().set(path+"pos1.x",(int) loc1.getX());
        Eventminigame.getInstance().getConfig().set(path+"pos1.y",(int) loc1.getY());
        Eventminigame.getInstance().getConfig().set(path+"pos1.z",(int) loc1.getZ());
        Eventminigame.getInstance().getConfig().set(path+"pos2.x",(int) loc2.getX());
        Eventminigame.getInstance().getConfig().set(path+"pos2.y",(int) loc2.getY());
        Eventminigame.getInstance().getConfig().set(path+"pos2.z",(int) loc2.getZ());
        Eventminigame.getInstance().saveConfig();
        Eventminigame.getInstance().reloadConfig();
    }

    //SETTER
    public void setLoc1(Location loc1){this.loc1=loc1;}
    public void setLoc2(Location loc2){this.loc2=loc2;}
    public void Rebuild(){
        int x1 = (int) loc1.getX();
        int y1 = (int) loc1.getZ();
        int z1 = (int) loc1.getY();
        int x2 = (int) loc2.getX();
        int y2 = (int) loc2.getY();
        int z2 = (int) loc2.getZ();
        int temp;
        if (x1>x2){
            temp=x1;
            x1=x2;
            x2=temp;
        }if (y1>y2){
            temp=y1;
            y1=y2;
            y2=temp;
        }if (z1>z2){
            temp=z1;
            z1=z2;
            z2=temp;
        }
        this.loc2=new Location(loc2.getWorld(),x2,y2,z2);
        this.loc1=new Location(loc1.getWorld(),x1,y1,z1);
    }
    //GETTER
    public Location getLoc1(){return loc1;}
    public Location getLoc2(){return loc2;}

    public static Area getInstanceFromConfig(String path){
        int x1 = Eventminigame.getInstance().getConfig().getInt(path+".pos1.x");
        int y1 = Eventminigame.getInstance().getConfig().getInt(path+".pos1.y");
        int z1 = Eventminigame.getInstance().getConfig().getInt(path+".pos1.z");
        int x2 = Eventminigame.getInstance().getConfig().getInt(path+".pos2.x");
        int y2 = Eventminigame.getInstance().getConfig().getInt(path+".pos2.y");
        int z2 = Eventminigame.getInstance().getConfig().getInt(path+".pos2.z");
        UUID uuid = UUID.fromString(Objects.requireNonNull(Eventminigame.getInstance().getConfig().getString("LadderRace.world.uuid")));
        World world = Bukkit.getWorld(uuid);
        Location loc1 = new Location(world,x1,y1,z1);
        Location loc2 = new Location(world,x2,y2,z2);
        return new Area(loc1,loc2);
    }
}
