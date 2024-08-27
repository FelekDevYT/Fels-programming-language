package main.java.net.felsstudio.fels.Modules.rayFels;

import com.raylib.Raylib;
import main.java.net.felsstudio.fels.Modules.Module;
import main.java.net.felsstudio.fels.lib.ArrayValue;
import main.java.net.felsstudio.fels.lib.Function;
import main.java.net.felsstudio.fels.lib.NumberValue;
import main.java.net.felsstudio.fels.lib.Value;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class rayFels implements Module {

    @Override
    public Map<String, Function> functions() {
        Raylib rl = new Raylib();
        return Map.ofEntries(
                entry("rfInitWindow", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.InitWindow(args[0].asInt(),args[1].asInt(),args[2].asString());
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfIsCloseRequest", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        if(rl.WindowShouldClose()) return NumberValue.ONE;
                        else return NumberValue.ZERO;
                    }
                }),
                entry("rfCloseWindow", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.CloseWindow();
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfSetFPS", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.SetTargetFPS(args[0].asInt());
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfBeginDrawing", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.BeginDrawing();
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfEndDrawing", new Function() {
                    @Override
                    public Value execute(Value... args){
                        rl.EndDrawing();
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfDrawText", new Function() {
                    @Override
                    public Value execute(Value... args){
                        rl.DrawText(args[0].asString(),
                                args[1].asInt(),
                                args[2].asInt(),
                                args[3].asInt(),
                                c(args[4].asInt(),
                                        args[5].asInt(),
                                        args[6].asInt(),
                                        255));
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfDrawLine", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.DrawLine(args[0].asInt(),
                                args[1].asInt(),
                                args[2].asInt(),
                                args[3].asInt(),
                                c(args[4].asInt(),
                                        args[5].asInt(),
                                        args[6].asInt(),
                                        255));
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfDrawRect", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.DrawRectangle(args[0].asInt(),
                                args[1].asInt(),
                                args[2].asInt(),
                                args[3].asInt(),
                                c(args[4].asInt(),
                                        args[5].asInt(),
                                        args[6].asInt(),
                                        255));
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfGetFPS", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        return NumberValue.of(rl.GetFPS());
                    }
                }),
                entry("rfDrawFPS", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.DrawFPS(args[0].asInt(),
                                args[1].asInt());
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfDrawGrid", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.DrawGrid(args[0].asInt(),
                                args[1].asInt());
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfClearBackground", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.ClearBackground(c(args[0].asInt(),
                                args[1].asInt(),
                                args[2].asInt(),
                                255));
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfIsKeyPressed", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        if(rl.IsKeyPressed(getKey(args[0].asString()))){
                            return NumberValue.ONE;
                        }
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfIsKeyDown", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        if(rl.IsKeyDown(getKey(args[0].asString()))){
                            return NumberValue.ONE;
                        }
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfIsKeyReleased", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        if(rl.IsKeyReleased(getKey(args[0].asString()))){
                            return NumberValue.ONE;
                        }
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfHideCursor", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.HideCursor();
                        return NumberValue.ZERO;
                    }
                }),
                entry("rfShowCursor", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        rl.ShowCursor();
                        return NumberValue.ZERO;
                    }
                }),
                entry("IsMouseDown", new Function() {
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
                }),
                entry("IsMouseUp", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        if(getMB(args[0].asString()) == 0){
                            return rl.IsMouseButtonUp(Raylib.MOUSE_BUTTON_LEFT)?
                                    NumberValue.ONE : NumberValue.ZERO;
                        }else if(getMB(args[0].asString()) == 1){
                            return rl.IsMouseButtonUp(Raylib.MOUSE_BUTTON_RIGHT)?
                                    NumberValue.ONE : NumberValue.ZERO;
                        }
                        return NumberValue.ZERO;
                    }
                }),
                entry("IsMouseReleased", new Function() {
                    @Override
                    public Value execute(Value... args) throws IOException {
                        if(getMB(args[0].asString()) == 0){
                            return rl.IsMouseButtonReleased(Raylib.MOUSE_BUTTON_LEFT)?
                                    NumberValue.ONE : NumberValue.ZERO;
                        }else if(getMB(args[0].asString()) == 1){
                            return rl.IsMouseButtonReleased(Raylib.MOUSE_BUTTON_RIGHT)?
                                    NumberValue.ONE : NumberValue.ZERO;
                        }
                        return NumberValue.ZERO;
                    }
                })
        );
    }

    @Override
    public Map<String, Value> constants() {
        return Map.of();
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
