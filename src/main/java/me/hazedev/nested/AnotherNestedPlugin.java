package me.hazedev.nested;

import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.UnknownDependencyException;

import java.util.Collections;

public class AnotherNestedPlugin extends NestedPlugin {

    @Override
    protected PluginDescriptionFile createDescription() throws InvalidDescriptionException {
        return new PluginDescriptionFileBuilder("AnotherNested", manager.getDescription().getVersion(), this.getClass().getName())
                .apiVersion("1.16")
                .softDepend(Collections.singletonList("ExampleNested"))
                .build();
    }

    @Override
    public void onEnable() {
        ExampleNestedPlugin exampleNested = manager.getPluginIfEnabled(ExampleNestedPlugin.class);
        if (exampleNested != null) {
            getLogger().info("The value in ExampleNested is " + exampleNested.value);
        } else {
            throw new UnknownDependencyException(ExampleNestedPlugin.class.getName());
        }
    }

}
