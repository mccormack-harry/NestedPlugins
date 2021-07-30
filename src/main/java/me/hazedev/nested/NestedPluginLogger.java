package me.hazedev.nested;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NestedPluginLogger extends Logger {

    public NestedPluginLogger(@NotNull Plugin plugin) {
        super(Optional.ofNullable(plugin.getDescription().getPrefix()).orElse(plugin.getDescription().getName()), null);
        this.setParent(plugin.getServer().getLogger());
        this.setLevel(Level.ALL);
    }

}
