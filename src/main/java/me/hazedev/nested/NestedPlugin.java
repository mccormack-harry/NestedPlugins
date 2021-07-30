package me.hazedev.nested;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

public abstract class NestedPlugin extends PluginBase {

    boolean isEnabled = false;
    protected NestedPluginManager manager;
    PluginDescriptionFile descriptionFile;
    Logger logger;
    private boolean naggable = true;

    @Override
    public final boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public @NotNull File getDataFolder() {
        return new File(manager.getDataFolder(), getDescription().getName());
    }

    protected abstract PluginDescriptionFile createDescription() throws InvalidDescriptionException;

    @Override
    public final @NotNull PluginDescriptionFile getDescription() {
        return descriptionFile;
    }

    @Override
    public final @NotNull PluginLoader getPluginLoader() {
        return manager;
    }

    @Override
    public final @NotNull Server getServer() {
        return manager.getServer();
    }

    @Override
    public final @NotNull Logger getLogger() {
        return this.logger;
    }

    @Override
    public boolean isNaggable() {
        return naggable;
    }

    @Override
    public void setNaggable(boolean naggable) {
        this.naggable = naggable;
    }

    @Override
    public @Nullable ChunkGenerator getDefaultWorldGenerator(@NotNull String s, @Nullable String s1) {
        return null;
    }

    @Override
    public void onLoad() {}

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    @Override
    @Deprecated
    public final @NotNull FileConfiguration getConfig() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public @Nullable InputStream getResource(@NotNull String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void saveConfig() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void saveDefaultConfig() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void saveResource(@NotNull String s, boolean b) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void reloadConfig() {
        throw new UnsupportedOperationException();
    }

}
