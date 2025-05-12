package net.felsstudio.fels.parser.optimization;

import net.felsstudio.fels.parser.ast.Node;

import java.io.IOException;

public interface Optimizable {

    Node optimize(Node node) throws IOException, InterruptedException;

    int optimizationsCount();

    String summaryInfo();
}
