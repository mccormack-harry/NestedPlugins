package me.hazedev.nested;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;

public abstract class NestedPluginManager extends JavaPlugin implements PluginLoader {

    private boolean acceptingNew = true;
    private final Map<Class<? extends NestedPlugin>, NestedPlugin> plugins = new HashMap<>();
    private List<Plugin> pluginManagerList;

    /*
    In this method we "load" all the nested plugins by adding them to the SimplePluginManager
    We have to use reflection to get access to the private field.
     */
    @Override
    public void onLoad() {
        SimplePluginManager pluginManager = (SimplePluginManager) Bukkit.getServer().getPluginManager();
        try {
            Field field = pluginManager.getClass().getDeclaredField("plugins");
            field.setAccessible(true);
            //noinspection unchecked
            pluginManagerList = (List<Plugin>) field.get(pluginManager);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }

        loadPlugins();
        // No longer able to load plugins
        acceptingNew = false;
    }

    // This methods implementation should be a series of calls to #loadPlugin
    public abstract void loadPlugins();

    public void loadPlugin(NestedPlugin plugin) {
        if (acceptingNew) {
            Class<? extends NestedPlugin> clazz = plugin.getClass();
            if (!plugins.containsKey(clazz)) {
                plugins.put(clazz, plugin);
                plugin.manager = this;
                try {
                    plugin.descriptionFile = plugin.createDescription();
                    plugin.logger = new NestedPluginLogger(plugin);
                } catch (InvalidDescriptionException e) {
                    getLogger().log(Level.SEVERE, "Failed to read plugin description for " + plugin.getClass().getName(), e);
                }
                plugin.getLogger().info("Loading " + plugin.getDescription().getFullName());
                try {
                    plugin.onLoad();
                } catch (Exception e) {
                    getLogger().log(Level.SEVERE, "Error occurred while loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", e);
                }
                pluginManagerList.add(plugin);
            } else {
                plugin.getLogger().warning("Plugin is already registered");
            }
        } else {
            throw new IllegalStateException("Plugins must only be registered in NestedPluginManager#registerPlugins");
        }
    }

    @Nullable
    private <T extends NestedPlugin> T getNestedPlugin(Class<T> clazz) {
        if (plugins.containsKey(clazz)) {
            return clazz.cast(plugins.get(clazz));
        }
        return null;
    }

    @Nullable
    public <T extends NestedPlugin> T getPluginIfEnabled(Class<T> clazz) {
        T plugin = getNestedPlugin(clazz);
        if (plugin != null && plugin.isEnabled()) {
            return plugin;
        }
        return null;
    }

    @Override
    public void enablePlugin(@NotNull Plugin nestedPlugin) {
        if (nestedPlugin instanceof NestedPlugin plugin) {
            plugin.getLogger().info("Enabling " + plugin.getDescription().getFullName());
            try {
                plugin.onEnable();
                plugin.isEnabled = true;
            } catch (Exception e) {
                getLogger().log(Level.SEVERE, "Error occurred while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", e);
            }
        } else {
            throw new IllegalArgumentException("Plugin is not associated with this PluginLoader");
        }
    }

    @Override
    public void disablePlugin(@NotNull Plugin plugin) {
        if (plugin instanceof NestedPlugin nestedPlugin) {
            try {
                nestedPlugin.onDisable();
                nestedPlugin.isEnabled = false;
            } catch (Exception e) {
                getLogger().log(Level.SEVERE, "Error occurred while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?)", e);
            }
        } else {
            throw new IllegalArgumentException("Plugin is not associated with this PluginLoader");
        }
    }

    @Override
    public @NotNull Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(@NotNull Listener listener, @NotNull Plugin plugin) {
        // Use JavaPluginLoader implementation
        return this.getPluginLoader().createRegisteredListeners(listener, plugin);
    }

    @Deprecated
    @Override
    public @NotNull Plugin loadPlugin(@NotNull File file) throws UnknownDependencyException {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public @NotNull PluginDescriptionFile getPluginDescription(@NotNull File file) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public @NotNull Pattern[] getPluginFileFilters() {
        return new Pattern[0];
    }
}
