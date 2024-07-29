package br.dev.phsaraiva.windCharge;

import br.dev.phsaraiva.windCharge.player.PlayerUtil;
import br.dev.phsaraiva.windCharge.tools.EnumTools;
import br.dev.phsaraiva.windCharge.tools.WindChargeItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public class WindCharge extends JavaPlugin implements CommandExecutor {

    private static WindCharge instance;
    public static final Material SNOWBALL = Material.valueOf("SNOW_BALL");

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

        //for server reload
        for (Player player : Bukkit.getOnlinePlayers()) {
            new PlayerUtil(player);
        }

        // registering listeners
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new WindChargeItem(), instance);


        // for Default Join
        pm.registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                new PlayerUtil(event.getPlayer());
            }
        }, instance);
        this.getCommand("legacytools").setExecutor(this);
    }

    public static WindCharge getInstance() {
        return instance;
    }

    public FileConfiguration getConfig() {
        return super.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(WindChargePerms.RELOAD)) {
            if (args.length == 0) {
                sendConfigMessage(sender, "messages.help");
            } else if (args[0].equalsIgnoreCase("give")) {
                if (sender instanceof Player) {
                    if (args.length > 3) {
                        this.giveCommand(args, sender, 4);
                    } else {
                        this.giveCommand(args, sender, 3);
                    }
                } else {
                    this.giveCommand(args, sender, 4);
                }
            } else if (args[0].equalsIgnoreCase("types")) {
                String s = "";
                for (EnumTools type : EnumTools.values()) {
                    s = s + type.name().toLowerCase(Locale.ENGLISH) + ", ";
                }
                sendConfigMessage(sender, "messages.types", s);
            }
        } else {
            sendConfigMessage(sender, "messages.perm");
        }
        return true;
    }

    private void giveCommand(String[] args, CommandSender sender, int length) {
        if (args.length < length) {
            sendConfigMessage(sender, "usage.give");
        } else {
            try {
                Player player = length != 3 ? Bukkit.getPlayer(args[3]) : (Player) sender;
                if (player == null) {
                    sendConfigMessage(sender, "messages.noPlayerFound");
                } else {
                    EnumTools type = EnumTools.valueOf(args[1].toUpperCase(Locale.ENGLISH));
                    Integer amount = Integer.parseInt(args[2]);
                    boolean flag = EnumTools.giveItem(player, type, amount);
                    if (flag) {
                        sendConfigMessage(sender, "messages.given");
                    } else {
                        sendConfigMessage(sender, "messages.inventoryFull");
                    }
                }
            } catch (Exception x) {
                sendConfigMessage(sender, "messages.incorrectInput", x.getMessage());
            }
        }
    }

    public void sendConfigMessage(CommandSender sender, String node) {
        if (this.getConfig().isList(node)) {
            for (String text : this.getConfig().getStringList(node)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString(node)));
        }
    }

    public void sendConfigMessage(CommandSender sender, String node, Object...objects) {
        if (this.getConfig().isList(node)) {
            for (String text : this.getConfig().getStringList(node)) {
                sender.sendMessage(String.format(ChatColor.translateAlternateColorCodes('&', text), objects));
            }
        } else {
            sender.sendMessage(String.format(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString(node)), objects));
        }
    }
}
