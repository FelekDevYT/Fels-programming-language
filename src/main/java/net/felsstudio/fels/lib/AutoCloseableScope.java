package main.java.net.felsstudio.fels.lib;

public final class AutoCloseableScope implements AutoCloseable {
    @Override
    public void close() {
        ScopeHandler.pop();
    }
}