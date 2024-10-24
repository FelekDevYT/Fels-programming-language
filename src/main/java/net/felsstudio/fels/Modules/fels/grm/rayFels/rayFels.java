package main.java.net.felsstudio.fels.Modules.fels.grm.rayFels;

import com.raylib.Raylib;
import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.*;

import java.io.IOException;

public class rayFels implements Module {

    @Override
    public void init() {
        Raylib rl = new Raylib();
        Functions.set("rfInitWindow", new Function() {
            @Override
            public Value execute(Value... args) {
                rl.InitWindow(args[0].asInt(),args[1].asInt(),args[2].asString());
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfIsCloseRequest", new Function() {
            @Override
            public Value execute(Value... args) {
                if(rl.WindowShouldClose()) return NumberValue.ONE;
                else return NumberValue.ZERO;
            }
        });
        Functions.set("rfCloseWindow", new Function() {
            @Override
            public Value execute(Value... args) {
                rl.CloseWindow();
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfSetFPS", new Function() {
            @Override
            public Value execute(Value... args) {
                rl.SetTargetFPS(args[0].asInt());
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfBeginDrawing", new Function() {
            @Override
            public Value execute(Value... args) {
                rl.BeginDrawing();
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfEndDrawing", new Function() {
            @Override
            public Value execute(Value... args){
                rl.EndDrawing();
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfDrawText", new Function() {
            @Override
            public Value execute(Value... args){
                ArrayValue value = (ArrayValue)args[4];
                rl.DrawText(args[0].asString(),
                        args[1].asInt(),
                        args[2].asInt(),
                        args[3].asInt(),
                        c(value.get(0).asInt(),
                                value.get(1).asInt(),
                                value.get(2).asInt(),
                                255));
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfDrawLine", new Function() {
            @Override
            public Value execute(Value... args) {
                ArrayValue value = (ArrayValue)args[4];
                rl.DrawLine(args[0].asInt(),
                        args[1].asInt(),
                        args[2].asInt(),
                        args[3].asInt(),
                        c(value.get(0).asInt(),
                                value.get(1).asInt(),
                                value.get(2).asInt(),
                                255));
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfDrawRect", new Function() {
            @Override
            public Value execute(Value... args) {
                ArrayValue value = (ArrayValue)args[4];
                rl.DrawRectangle(args[0].asInt(),
                        args[1].asInt(),
                        args[2].asInt(),
                        args[3].asInt(),
                        c(value.get(0).asInt(),
                                value.get(1).asInt(),
                                value.get(2).asInt(),
                                255));
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfGetFPS", new Function() {
            @Override
            public Value execute(Value... args) {
                return NumberValue.of(rl.GetFPS());
            }
        });
        Functions.set("rfDrawFPS", new Function() {
            @Override
            public Value execute(Value... args) {
                rl.DrawFPS(args[0].asInt(),
                        args[1].asInt());
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfDrawGrid", new Function() {
            @Override
            public Value execute(Value... args) {
                rl.DrawGrid(args[0].asInt(),
                        args[1].asInt());
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfClearBackground", new Function() {
            @Override
            public Value execute(Value... args) {
                ArrayValue value = (ArrayValue)args[0];
                rl.ClearBackground(c(value.get(0).asInt(),
                        value.get(1).asInt(),
                        value.get(2).asInt(),
                        255));
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfIsKeyPressed", new Function() {
            @Override
            public Value execute(Value... args) {
                if(rl.IsKeyPressed(getKey(args[0].asString()))){
                    return NumberValue.ONE;
                }
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfIsKeyDown", new Function() {
            @Override
            public Value execute(Value... args) {
                if(rl.IsKeyDown(getKey(args[0].asString()))){
                    return NumberValue.ONE;
                }
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfIsKeyReleased", new Function() {
            @Override
            public Value execute(Value... args) {
                if(rl.IsKeyReleased(getKey(args[0].asString()))){
                    return NumberValue.ONE;
                }
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfHideCursor", new Function() {
            @Override
            public Value execute(Value... args) {
                rl.HideCursor();
                return NumberValue.ZERO;
            }
        });
        Functions.set("rfShowCursor", new Function() {
            @Override
            public Value execute(Value... args) {
                rl.ShowCursor();
                return NumberValue.ZERO;
            }
        });
        Functions.set("IsMouseDown", new Function() {
            @Override
            public Value execute(Value... args){
                if(getMB(args[0].asString()) == 0){
                    return rl.IsMouseButtonDown(Raylib.MOUSE_BUTTON_LEFT)?
                            NumberValue.ONE : NumberValue.ZERO;
                }else if(getMB(args[0].asString()) == 1){
                    return rl.IsMouseButtonDown(Raylib.MOUSE_BUTTON_RIGHT)?
                            NumberValue.ONE : NumberValue.ZERO;
                }
                return NumberValue.ZERO;
            }
        });
        Functions.set("IsMouseUp", new Function() {
            @Override
            public Value execute(Value... args) {
                if(getMB(args[0].asString()) == 0){
                    return rl.IsMouseButtonUp(Raylib.MOUSE_BUTTON_LEFT)?
                            NumberValue.ONE : NumberValue.ZERO;
                }else if(getMB(args[0].asString()) == 1){
                    return rl.IsMouseButtonUp(Raylib.MOUSE_BUTTON_RIGHT)?
                            NumberValue.ONE : NumberValue.ZERO;
                }
                return NumberValue.ZERO;
            }
        });
        Functions.set("IsMouseReleased", new Function() {
            @Override
            public Value execute(Value... args) {
                if(getMB(args[0].asString()) == 0){
                    return rl.IsMouseButtonReleased(Raylib.MOUSE_BUTTON_LEFT)?
                            NumberValue.ONE : NumberValue.ZERO;
                }else if(getMB(args[0].asString()) == 1){
                    return rl.IsMouseButtonReleased(Raylib.MOUSE_BUTTON_RIGHT)?
                            NumberValue.ONE : NumberValue.ZERO;
                }
                return NumberValue.ZERO;
            }
        });
    }

    private static int getMB(final String name){
        return switch (name){
            case "LEFT_MOUSE"->{
                yield 0;
            }
            case "RIGHT_MOUSE"->{
                yield 1;
            }
            default -> {
                yield  -1;
            }
        };
    }

    private static int getKey(final String name){
        return switch (name){
            default -> throw new IllegalStateException("Unexpected value: " + name);
            case "KEY_A"-> {
                yield Raylib.KEY_A;
            }
            case "KEY_W"->{
                yield Raylib.KEY_W;
            }
            case "KEY_S"->{
                yield Raylib.KEY_S;
            }
            case "KEY_D"->{
                yield Raylib.KEY_D;
            }
            case "ARROW_UP"-> {
                yield Raylib.KEY_UP;
            }
            case "ARROW_DOWN"->{
                yield Raylib.KEY_DOWN;
            }
            case "ARROW_RIGHT"->{
                yield Raylib.KEY_RIGHT;
            }
            case "ARROW_LEFT"->{
                yield Raylib.KEY_LEFT;
            }
            case "KEY_SPACE"->{
                yield Raylib.KEY_SPACE;
            }
            case "KEY_LEFT_SHIFT"->{
                yield Raylib.KEY_LEFT_SHIFT;
            }
            case "KEY_RIGHT_SHIFT"->{
                yield Raylib.KEY_RIGHT_SHIFT;
            }
            case "KEY_ESCAPE"->{
                yield Raylib.KEY_ESCAPE;
            }
            case "KEY_ONE"->{
                yield Raylib.KEY_ONE;
            }
            case "KEY_TWO"->{
                yield Raylib.KEY_TWO;
            }
            case "KEY_THREE"->{
                yield Raylib.KEY_THREE;
            }
            case "KEY_FOUR"->{
                yield Raylib.KEY_FOUR;
            } case "KEY_FIVE"->{
                yield Raylib.KEY_FIVE;
            }
            case "KEY_SIX"->{
                yield Raylib.KEY_SIX;
            }
            case "KEY_SEVEN"->{
                yield Raylib.KEY_SEVEN;
            }
            case "KEY_EIGHT"->{
                yield Raylib.KEY_EIGHT;
            }
            case "KEY_NINE"->{
                yield Raylib.KEY_NINE;
            }
            case "KEY_ZERO"->{
                yield Raylib.KEY_ZERO;
            }
        };
    }

    private static Raylib.Color c(int r, int g, int b, int a) {
        return new Raylib.Color().r((byte) r).g((byte) g).b((byte) b).a((byte) a);
    }
}
