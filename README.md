# Nested Plugins
This repository showcases a spigot plugin which has the ability to contain multiple plugins.

An example of when this might be useful, is if you are writing a collection of plugins for a single server.

The benefit of doing this rather than just writing one plugin with loads of features is that each feature has its own
plugin instance for the purpose of registering listeners, tasks etc. 
In addition, each plugin is independent meaning:
- They can have separate dependencies
- They can depend on one another
- If one plugin has an error onEnable, the rest will be unaffected
- Each has their own DataFolder