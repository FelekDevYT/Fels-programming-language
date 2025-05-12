package net.felsstudio.fels.parser.ast;

import net.felsstudio.fels.lib.ClassDeclarations;
import net.felsstudio.fels.lib.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ClassDeclarationStatement implements Statement {

    public static String name;
    public static List<FunctionDefineStatement> methods;
    public static List<AssignmentExpression> fields;
    public static FunctionDefineStatement constructor; // Специальное поле для конструктора

    public ClassDeclarationStatement(String name) {
        this.name = name;
        this.methods = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.constructor = null;
    }

    public void addMethod(FunctionDefineStatement method) {
        if ("init".equals(method.name)) {
            this.constructor = method;
        } else {
            methods.add(method);
        }
    }

    public void addField(AssignmentExpression field) {
        fields.add(field);
    }

    public String getName() {
        return name;
    }

    public List<FunctionDefineStatement> getMethods() {
        return methods;
    }

    public List<AssignmentExpression> getFields() {
        return fields;
    }

    public FunctionDefineStatement getConstructor() {
        return constructor;
    }

    @Override
    public void execute() {
        ClassDeclarations.set(name, this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) throws IOException, InterruptedException {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return String.format(
                "class %s {\n  Fields: %s\n  Constructor: %s\n  Methods: %s\n}",
                name, fields, (constructor != null ? constructor : "None"), methods
        );
    }
}