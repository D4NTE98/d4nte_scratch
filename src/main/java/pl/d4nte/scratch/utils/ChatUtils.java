package pl.d4nte.scratch.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    public static void send (String msg, Player p) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static String fixColor(String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text.replace(">>", ">").replace("<<", "<").replace("*", "*").replace("{O}", "[]"));
    }

    public static List<String> fixColor(final List<String> list)
    {
        list.replaceAll(ChatUtils::fixColor);
        return list;
    }

    public static void broadcast(String string){
        Bukkit.getOnlinePlayers().forEach((p) -> {
            send(string, p);
        });
    }

    public static String hexColor(String text) {
        Pattern pattern = Pattern.compile("(&?#[0-9a-fA-F]{6})");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String hexColor = text.substring(matcher.start(), matcher.end());
            hexColor = hexColor.replace("&", "");
            StringBuilder bukkitColorCode = new StringBuilder('\u00A7' + "x");
            for (int i = 1; i < hexColor.length(); i++) {
                bukkitColorCode.append(String.valueOf('\u00A7') + hexColor.charAt(i));
            }
            String bukkitColor = bukkitColorCode.toString().toLowerCase();
            text = text.replaceAll(hexColor, bukkitColor);
            matcher.reset(text);
        }
        return text;
    }
}