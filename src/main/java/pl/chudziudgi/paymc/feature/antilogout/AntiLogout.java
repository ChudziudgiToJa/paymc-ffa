package pl.chudziudgi.paymc.feature.antilogout;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class AntiLogout {

    private final UUID identifier;
    @Setter
    private long leftTime;

    public AntiLogout(final UUID identifier, final long time){
        this.identifier = identifier;
        this.leftTime = time;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(this.identifier);
    }
}
