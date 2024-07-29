package br.dev.phsaraiva.windCharge.util;

import br.dev.phsaraiva.windCharge.util.CooldownManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CooldownManagerLegacy implements CooldownManager {


    @Override
    public boolean hasCooldown(Player player, Material material) {
        return false;
    }

    @Override
    public int getCooldown(Player player, Material material) {
        return 0;
    }

    @Override
    public void setCooldown(Player player, Material material, int ticks) {

    }

	/*@Override
	public boolean hasCooldown(Player player, Material material) {
		EntityPlayer ep = ((CraftPlayer) player).getHandle();
		return ep.getCooldownTracker().a(CraftMagicNumbers.getItem(material));
	}

	@Override
	public int getCooldown(Player player, Material material) {
		EntityPlayer ep = ((CraftPlayer) player).getHandle();
		return (int) ep.getCooldownTracker().a(CraftMagicNumbers.getItem(material), 0.0F) * 20;
	}

	@Override
	public void setCooldown(Player player, Material material, int ticks) {
		EntityPlayer ep = ((CraftPlayer) player).getHandle();
		ep.getCooldownTracker().a(CraftMagicNumbers.getItem(material), ticks);
	}*/

}