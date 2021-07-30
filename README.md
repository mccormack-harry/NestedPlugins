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

![image](https://user-images.githubusercontent.com/14370347/127704199-399e52ca-38ca-4e8f-b2d6-72f5c7c396f8.png)
```
[20:43:52 INFO]: [NestedPlugins] Loading NestedPlugins v1.0-SNAPSHOT
[20:43:52 INFO]: [ExampleNested] Loading ExampleNested v1.0-SNAPSHOT
[20:43:52 INFO]: [AnotherNested] Loading AnotherNested v1.0-SNAPSHOT
...
[20:43:53 INFO]: [NestedPlugins] Enabling NestedPlugins v1.0-SNAPSHOT
[20:43:53 INFO]: [ExampleNested] Enabling ExampleNested v1.0-SNAPSHOT
[20:43:53 INFO]: [AnotherNested] Enabling AnotherNested v1.0-SNAPSHOT
[20:43:53 INFO]: [AnotherNested] The value in ExampleNested is 5
```
