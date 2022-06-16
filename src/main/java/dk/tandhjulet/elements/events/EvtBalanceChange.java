package dk.tandhjulet.elements.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import dk.tandhjulet.SkEssentials;

import net.ess3.api.events.UserBalanceUpdateEvent;


public class EvtBalanceChange extends SkriptEvent {


    static {
        if(Bukkit.getPluginManager().getPlugin("Essentials") != null) {
            Skript.registerEvent("Balance Change", EvtBalanceChange.class, UserBalanceUpdateEvent.class, "[player] balance change");
            SkEssentials.getPlugin().getLogger().info("Hooked into Essentials for economy");
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "on balance change";
    }

    @Override
    public boolean check(Event e) {
        return true;
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, ParseResult parser) {
        return true;
    }
    
}
