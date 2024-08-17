package main.java.net.felsstudio.fels.lib;

import main.java.net.felsstudio.fels.Modules.Module;

public final class ModuleLoader {
    private static final String PACKAGE = "main.java.net.felsstudio.fels.Modules.%s.%s";

    private ModuleLoader() { }

    public static Module load(String name) {
        try {
            return (Module) Class.forName(String.format(PACKAGE, name, name))
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to load module " + name, ex);
        }
    }

    public static void loadAndUse(String name) {
        final var rootScope = ScopeHandler.rootScope();
        if (rootScope.isModuleLoaded(name)) return;

        final Module module = load(name);
        rootScope.getConstants().putAll(module.constants());
        rootScope.getFunctions().putAll(module.functions());
    }
}