package pl.chudziudgi.paymc.feature.antilogout;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.chudziudgi.paymc.util.TimeType;

import java.util.Arrays;
import java.util.Set;

public class AntiLogoutManager {

    private final Set<AntiLogout> antiLogoutSet;

    public AntiLogoutManager() {
        this.antiLogoutSet = Sets.newLinkedHashSet();
    }

    public void createCombat(final Player player, TimeType timeEnum, final int time) {
        final AntiLogout antiLogout = this.getCombat(player);
        if (antiLogout != null) {
            this.antiLogoutSet.remove(antiLogout);
        }
        this.antiLogoutSet.add(new AntiLogout(player.getUniqueId(), System.currentTimeMillis() + timeEnum.getTime(time + 1)));
    }

    public boolean inCombat(Player player) {
        final AntiLogout antiLogout = getCombat(player);
        if (antiLogout == null) {
            return false;
        }
        if (antiLogout.getLeftTime() > 0) {
            return true;
        }
        this.removeCombat(antiLogout);
        return false;
    }

    public void removeCombat(AntiLogout antiLogout) {
        if (this.getCombat(antiLogout.getPlayer()) != null) {
            antiLogoutSet.remove(antiLogout);
        }
    }

    public AntiLogout getCombat(final Player player) {
        return this.antiLogoutSet.stream().filter(antiLogout -> antiLogout.getIdentifier().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public boolean isBlockedBlock(Material material) {
        return Arrays.asList(Material.TRAPPED_CHEST, Material.CHEST, Material.CRAFTING_TABLE, Material.ENDER_CHEST, Material.FURNACE, Material.SMOKER, Material.BLAST_FURNACE, Material.DISPENSER, Material.DROPPER, Material.BARREL).contains(material);
    }
}
