package com.skeezy.apd.advancedpermissiondumper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AdvancedPermissionDumper extends JavaPlugin implements TabExecutor {

    @Override
    public void onEnable() {
        // Register the command
        this.getCommand("apd").setExecutor(this);
        this.getCommand("apd").setTabCompleter(new PluginTabCompleter());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &a&lAdvanced Permissions Dumper"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage: &f/apd <plugin|ALL>"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Dumps the permissions of a plugin to a file."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&omade by sforskeezy | github.com/sforskeezy"));

            return true;
        }

        PluginManager pluginManager = Bukkit.getPluginManager();
        String pluginName = args[0];
        YamlConfiguration yamlConfig = new YamlConfiguration();

        if (pluginName.equalsIgnoreCase("ALL")) {
            for (Plugin plugin : pluginManager.getPlugins()) {
                dumpPluginPermissions(plugin, yamlConfig);
            }
        } else {
            Plugin plugin = pluginManager.getPlugin(pluginName);
            if (plugin != null) {
                dumpPluginPermissions(plugin, yamlConfig);
            } else {
                sender.sendMessage("§cPlugin not found: " + pluginName);
                return true;
            }
        }

        saveYamlFile(yamlConfig, sender);
        return true;
    }

    private void dumpPluginPermissions(Plugin plugin, YamlConfiguration yamlConfig) {
        String pluginName = plugin.getName();
        yamlConfig.set(pluginName, null);

        for (Permission permission : plugin.getDescription().getPermissions()) {
            String permissionName = permission.getName();
            String permissionDescription = permission.getDescription();
            PermissionDefault permissionDefault = permission.getDefault();

            yamlConfig.set(pluginName + "." + permissionName + ".description", permissionDescription);
            yamlConfig.set(pluginName + "." + permissionName + ".default", permissionDefault.toString());
        }
    }


    private void saveYamlFile(YamlConfiguration yamlConfig, CommandSender sender) {
        File file = new File(this.getDataFolder(), "permissions-dump.yml");

        try {
            yamlConfig.save(file);
            sender.sendMessage("§aPermissions have been dumped to " + file.getAbsolutePath());
        } catch (IOException e) {
            sender.sendMessage("§cFailed to save permissions dump.");
            e.printStackTrace();
        }
    }

    private class PluginTabCompleter implements TabCompleter {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            List<String> completions = new ArrayList<>();
            if (args.length == 1) {
                completions.add("ALL");
                for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                    completions.add(plugin.getName());
                }
            }
            return completions;
        }
    }
}
