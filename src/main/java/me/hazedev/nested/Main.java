package me.hazedev.nested;

public class Main extends NestedPluginManager {

    @Override
    public void loadPlugins() {
        loadPlugin(new ExampleNestedPlugin());
        loadPlugin(new AnotherNestedPlugin());
    }

}
