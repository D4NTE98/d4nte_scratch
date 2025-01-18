package pl.d4nte.scratch.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.file.FileConfiguration;
import pl.d4nte.scratch.ScratchPlugin;
import pl.d4nte.scratch.utils.ChatUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ScratchListener implements Listener {

    private final ScratchPlugin plugin;

    public ScratchListener(ScratchPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.getType() == Material.PAPER && isScratch(itemInHand)) {
            handleScratchClick(player);
        }
    }

    private boolean isScratch(ItemStack item) {
        FileConfiguration config = plugin.getConfig();
        String message = ChatUtils.fixColor(config.getString("messages.item_name"));
        return item != null && item.hasItemMeta() &&
                item.getItemMeta().hasDisplayName() &&
                item.getItemMeta().getDisplayName().equals(message);
    }

    private void handleScratchClick(Player player) {
        FileConfiguration config = plugin.getConfig();
        List<String> list = config.getStringList("items");

        Random random = new Random();
        String newItem = list.get(random.nextInt(list.size()));

        Optional<ItemStack> itemOptional = createItemFromConfig(newItem);

        itemOptional.ifPresent(item -> {
            player.getInventory().setItemInMainHand(item);
            String message = ChatUtils.fixColor(config.getString("messages.scratch_received"));
            message = message.replace("%item%", item.getType().toString());
            player.sendMessage(message);
        });
    }

    private Optional<ItemStack> createItemFromConfig(String przedmiot) {
        try {
            Material material = Material.valueOf(przedmiot);
            ItemStack item = new ItemStack(material);
            return Optional.of(item);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Nieprawidłowy materiał: " + przedmiot);
            return Optional.empty();
        }
    }
}