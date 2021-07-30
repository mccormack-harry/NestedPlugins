package me.hazedev.nested;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.io.StringReader;
import java.util.List;

public class PluginDescriptionFileBuilder extends YamlConfiguration {

    public PluginDescriptionFileBuilder(@NotNull String name, @NotNull String version, @NotNull String mainClass) {
        set("name", name);
        set("version", version);
        set("main", mainClass);
    }

    public PluginDescriptionFileBuilder(@NotNull ConfigurationSection template) {
        for (String key: template.getKeys(false)) {
            set(key, template.get(key));
        }
    }

    public @NotNull PluginDescriptionFileBuilder description(String description) {
        set("description", description);
        return this;
    }

    public PluginDescriptionFileBuilder apiVersion(String apiVersion) {
        set("api-version", apiVersion);
        return this;
    }

    public PluginDescriptionFileBuilder loadAt(String load) {
        set("load", load);
        return this;
    }

    public @NotNull PluginDescriptionFileBuilder author(String author) {
        set("author", author);
        return this;
    }

    public @NotNull PluginDescriptionFileBuilder authors(List<String> authors) {
        set("authors", authors);
        return this;
    }

    public PluginDescriptionFileBuilder website(String website) {
        set("website", website);
        return this;
    }

    public PluginDescriptionFileBuilder depend(List<String> dependencies) {
        set("depend", dependencies);
        return this;
    }

    public PluginDescriptionFileBuilder prefix(String prefix) {
        set("prefix", prefix);
        return this;
    }

    public PluginDescriptionFileBuilder softDepend(List<String> softDependencies) {
        set("softdepend", softDependencies);
        return this;
    }

    public PluginDescriptionFileBuilder loadBefore(List<String> loadBefore) {
        set("loadbefore", loadBefore);
        return this;
    }

    public PluginDescriptionFileBuilder libraries(List<String> libraries) {
        set("libraries", libraries);
        return this;
    }

    public PluginDescriptionFileBuilder command(String name, ConfigurationSection commandDescription) {
        set("commands." + name, commandDescription);
        return this;
    }
    
    public PluginDescriptionFileBuilder permission(String name, ConfigurationSection permissionDescription) {
        set("permissions." + name, permissionDescription);
        return this;
    }

    public @NotNull PluginDescriptionFileBuilder contributors(List<String> contributors) {
        set("contributors", contributors);
        return this;
    }

    public PluginDescriptionFileBuilder provides(List<String> provides) {
        set("provides", provides);
        return this;
    }

    public @NotNull PluginDescriptionFile build() throws InvalidDescriptionException {
        return new PluginDescriptionFile(new StringReader(saveToString()));
    }

    public static class CommandDescription extends MemoryConfiguration {
        
        public CommandDescription description(String description) {
            set("description", description);
            return this;
        }

        public CommandDescription aliases(List<String> aliases) {
            set("aliases", aliases);
            return this;
        }

        public CommandDescription permission(String permission) {
            set("permission", permission);
            return this;
        }

        public CommandDescription permissionMessage(String permissionMessage) {
            set("permission-message", permissionMessage);
            return this;
        }

        public CommandDescription usage(String usage) {
            set("usage", usage);
            return this;
        }
        
    }

    public static class PermissionDescription extends MemoryConfiguration {

        public PermissionDescription description(String description) {
            set("description", description);
            return this;
        }

        public PermissionDescription default_(String default_) {
            set("default", default_);
            return this;
        }

        public PermissionDescription addChild(String child, String default_) {
            set("children." + child, default_);
            return this;
        }

    }
    
}
