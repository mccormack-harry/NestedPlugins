package me.hazedev.nested;

import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginDescriptionFile;

public class ExampleNestedPlugin extends NestedPlugin {

    public final int value = 5;

    @Override
    protected PluginDescriptionFile createDescription() throws InvalidDescriptionException {
        return new PluginDescriptionFileBuilder("ExampleNested", manager.getDescription().getVersion(), getClass().getName())
                .apiVersion("1.16")
                .build();
    }

}
