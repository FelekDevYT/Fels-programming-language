package main.java.net.felsstudio.fels.parser;

import main.java.net.felsstudio.fels.Console;
import main.java.net.felsstudio.fels.parser.ast.Node;
import main.java.net.felsstudio.fels.parser.ast.Statement;
import main.java.net.felsstudio.fels.parser.optimization.ConstantFolding;
import main.java.net.felsstudio.fels.parser.optimization.ConstantPropagation;
import main.java.net.felsstudio.fels.parser.optimization.DeadCodeElimination;
import main.java.net.felsstudio.fels.parser.optimization.ExpressionSimplification;
import main.java.net.felsstudio.fels.parser.optimization.InstructionCombining;
import main.java.net.felsstudio.fels.parser.optimization.Optimizable;
import main.java.net.felsstudio.fels.parser.optimization.SummaryOptimization;

import java.io.IOException;

public final class Optimizer {

    private Optimizer() { }

    public static Statement optimize(Statement statement, int level, boolean showSummary) throws IOException, InterruptedException {
        if (level == 0) return statement;

        final Optimizable optimization = new SummaryOptimization(new Optimizable[] {
            new ConstantFolding(),
            new ConstantPropagation(),
            new DeadCodeElimination(),
            new ExpressionSimplification(),
            new InstructionCombining()
        });

        Node result = statement;
        if (level >= 9) {
            int iteration = 0, lastModifications = 0;
            do {
                lastModifications = optimization.optimizationsCount();
                result = optimization.optimize(result);
                iteration++;
            } while (lastModifications != optimization.optimizationsCount());
            if (showSummary)
                Console.print("Performs " + iteration + " optimization iterations");
        } else {
            for (int i = 0; i < level; i++) {
                result = optimization.optimize(result);
            }
        }
        if (showSummary) {
            Console.println(optimization.summaryInfo());
        }
        return (Statement) result;
    }
}