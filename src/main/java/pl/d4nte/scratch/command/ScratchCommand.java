package pl.d4nte.scratch.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.d4nte.scratch.ScratchPlugin;
import pl.d4nte.scratch.utils.ChatUtils;

public class ScratchCommand implements CommandExecutor {

    private final ScratchPlugin plugin;

    public ScratchCommand(ScratchPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ("zdrapka".equalsIgnoreCase(command.getName())) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command_only_player")));
                return false;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("d4nte.scratch")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no_permission")));
                return false;
            }
            if (args.length != 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command_usage")));
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.player_not_online").replace("%player%", args[0])));
                return false;
            }

            target.getInventory().addItem(createScratchItem());

            String targetMessage = plugin.getConfig().getString("messages.scratch_give").replace("%player%", player.getName());
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', targetMessage));

            return true;
        }
        return false;
    }

    public ItemStack createScratchItem() {
        ItemStack scratch = new ItemStack(Material.PAPER);
        FileConfiguration config = plugin.getConfig();
        ItemMeta meta = scratch.getItemMeta();
        if (meta != null) {
            String message = ChatUtils.fixColor(config.getString("messages.item_name"));
            meta.setDisplayName(message);
            scratch.setItemMeta(meta);
        }
        return scratch;
    }
}