package dk.tandhjulet.elements.expressions.koner;

import javax.annotation.Nullable;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class KoneClassInfo {
    static {
        Classes.registerClass(new ClassInfo<>(Kone.class, "kone")
                .user("kone?")
                .name("kone")
                .parser(new Parser<Kone>() {
                    @Override
                    @Nullable
                    public Kone parse(String input, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext contect) {
                        return false;
                    }

                    @Override
                    public String toVariableNameString(Kone kone) {
                        return kone.getName();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".*?";
                    }

                    @Override
                    public String toString(Kone node, int flags) {
                        return toVariableNameString(node);
                    }
                }));
    }
}
