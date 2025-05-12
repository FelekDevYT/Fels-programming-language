package net.felsstudio.fels.parser.ast;

import org.jetbrains.annotations.Nullable;

public class PassStatement implements Statement{
    @Override
    public void execute() {
        //This is pass function
        //This function do nothing!
    }

    @Override
    public void accept(@Nullable Visitor visitor) {

    }

    @Override
    public <R, T> R accept(@Nullable ResultVisitor<R, T> visitor, T input) {
        return null;
    }
}