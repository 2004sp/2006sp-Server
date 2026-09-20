package com.rs2.bot;

import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.util.GameUtil;

public final class CastleWarsBotChat {
    private static final String[] WAITING = new String[]{
        "gl all", "good luck", "ready?", "lets go", "how long left",
        "nearly time", "rush mid first", "someone def flag", "ill go flag",
        "who is defending", "need more sara", "need more zammy", "this should be good",
        "get ready", "go straight mid", "dont all rush flag", "watch tunnels",
        "someone use cata", "bring bandages", "save run", "im going underground",
        "ill defend", "ill rush", "meet at mid", "stack mid", "push together",
        "dont feed", "watch our flag", "they look strong", "we got this",
        "hope teams are even", "need a flag runner", "any defenders?", "cata anyone?",
        "tunnels could work", "ill take east side", "ill take west side", "follow me out",
        "lets get a quick cap", "no afk pls", "ready sara", "ready zammy",
        "good team", "this cave is packed", "big game", "glhf"
    };

    private static final String[] GENERAL = new String[]{
        "push up", "keep moving", "stay together", "split up", "watch the flag",
        "incoming", "they are pushing", "help base", "help mid", "push mid",
        "go go go", "nice one", "nice", "good fight", "gf", "lol",
        "keep pressure", "dont stop", "fall back", "regroup", "spread out",
        "watch stairs", "watch ladder", "watch tunnels", "someone defend",
        "someone rush flag", "need help here", "team on me", "follow up",
        "they are low", "finish them", "dont chase too far", "hold this side",
        "push north", "push south", "take the long way", "use the other stairs",
        "bandage up", "save run energy", "keep our flag safe", "where is their flag",
        "who has flag", "flag status?", "need more at base", "need more mid",
        "clear the way", "nice push", "good defence", "keep going",
        "we can cap", "one more push", "dont bunch up", "use both routes"
    };

    private static final String[] MID = new String[]{
        "rush mid", "hold mid", "stack mid", "fight mid", "mid now",
        "help mid", "push mid", "mid is clear", "mid is packed", "take mid",
        "dont lose mid", "meet mid", "group mid", "theyre at mid", "more mid",
        "mid needs help", "push through mid", "clear mid", "hold the center",
        "fight here", "focus one", "pile this one", "switch target", "stay on them",
        "dont run", "keep pressure mid", "mid push now", "theyre falling back",
        "chase to river", "watch both sides", "dont overextend", "regroup center",
        "rangers spread", "mages at back", "melee push", "keep center control",
        "nice mid", "good pile", "another wave", "incoming mid"
    };

    private static final String[] COMBAT = new String[]{
        "fight!", "on me", "help", "pile", "focus", "switch", "theyre low",
        "almost dead", "finish them", "dont let them run", "nice hit", "good hit",
        "come here", "stand and fight", "gf soon", "eat up", "pray up",
        "watch mage", "watch range", "melee on them", "freeze them", "bind them",
        "keep hitting", "one more", "got them", "nice kill", "back on target",
        "dont split", "same target", "push them back", "hold here", "stay close",
        "theyre running", "chase a bit", "leave them", "new target", "next one"
    };

    private static final String[] FLAG_ATTACK = new String[]{
        "get the flag", "rush flag", "flag run", "up the stairs", "go top floor",
        "their flag is open", "flag room now", "someone take flag", "cover runner",
        "clear their base", "push their castle", "get inside", "take the stairs",
        "use ladder", "second floor clear", "top floor clear", "runner go",
        "flag is there", "grab it", "get out quick", "escort flag",
        "clear a path", "meet runner mid", "protect carrier", "dont block runner",
        "open route", "enemy base weak", "quick flag", "cap this"
    };

    private static final String[] FLAG_CARRIER = new String[]{
        "got flag!", "i got flag", "cover me", "escort me", "need escort",
        "clear mid for me", "coming home", "runner coming", "protect flag",
        "dont let me die", "meet me mid", "clear stairs", "open our base",
        "im heading back", "flag coming home", "push with me", "block them",
        "need bandage", "almost home", "one floor left", "cover the ladder",
        "cover stairs", "get them off me", "runner low", "keep path clear",
        "home side now", "nearly there", "our flag safe?", "can we cap?"
    };

    private static final String[] DEFENCE = new String[]{
        "def flag", "def base", "incoming base", "theyre in our castle",
        "enemy upstairs", "protect flag", "flag room help", "base needs help",
        "hold stairs", "hold ladder", "barricade stairs", "block entrance",
        "dont leave flag", "one runner inside", "two inside", "pile the runner",
        "our flag!", "stop carrier", "chase carrier", "cut them off mid",
        "guard top floor", "watch tunnel ladder", "someone stay flag",
        "more defenders", "base clear", "nice defence", "flag safe",
        "repair defence", "replace barricade", "dont chase from base"
    };

    private static final String[] UNDERGROUND = new String[]{
        "going tunnels", "tunnel push", "underground route", "clear rocks",
        "rocks ahead", "need pickaxe", "need explosive", "tunnel is blocked",
        "clearing tunnel", "tunnel clear", "come underground", "follow tunnel",
        "split tunnels", "watch cave in", "collapse behind us", "block this tunnel",
        "enemy underground", "fight in tunnel", "tunnel needs help", "secret route",
        "almost at ladder", "enemy ladder ahead", "going up", "coming back tunnel",
        "carrier underground", "escort tunnels", "clear return route",
        "dont collapse on us", "wait for team", "tunnel route open"
    };

    private static final String[] ENGINEERING = new String[]{
        "boom", "barricade down", "clear barricade", "placing barricade",
        "block this tile", "need explosive", "got explosives", "breach here",
        "opening route", "defence set", "barricade up", "break their barricade",
        "rocks cleared", "collapse it", "engineering here", "make a gap",
        "route blocked", "route open", "hold the choke", "block the choke",
        "save an explosive", "need another barricade", "good blockade",
        "breach complete", "clear path"
    };

    private static final String[] CATAPULT = new String[]{
        "cata ready", "using catapult", "incoming rock", "fire!",
        "cata firing", "rock away", "aim mid", "aim their push", "hit mid",
        "nice cata hit", "reload cata", "need rocks", "bring rocks",
        "catapult down", "repair cata", "cata fixed", "protect catapult",
        "enemy on cata", "sabotage their cata", "their cata is down",
        "cata operational", "another shot", "clear the catapult", "watch friendly fire"
    };

    private static final String[] SCORE = new String[]{
        "scored!", "nice cap", "great cap", "good flag run", "thats one",
        "again again", "push another", "nice team", "good escort", "easy cap",
        "well played", "keep pressure", "one more flag", "reset and push",
        "back to mid", "def now", "protect the lead", "need another cap",
        "good job runner", "clean score", "nice route", "great defence too"
    };

    private static final String[] SARADOMIN = new String[]{
        "sara ftw", "go sara", "sara push", "sara mid", "sara defend",
        "for saradomin", "sara flag run", "nice sara", "sara together"
    };

    private static final String[] ZAMORAK = new String[]{
        "zammy ftw", "go zammy", "zammy push", "zammy mid", "zammy defend",
        "for zamorak", "zammy flag run", "nice zammy", "zammy together"
    };

    private CastleWarsBotChat() {
    }

    public static void sayContextual(BotPlayer bot) {
        if (CastleWarsManager.isWaitingPlayer(bot)) {
            say(bot, WAITING);
            return;
        }
        if (!CastleWarsManager.isInGame(bot)) {
            return;
        }
        if (CastleWarsManager.isCarryingEnemyFlag(bot)) {
            sayFlagCarrier(bot);
            return;
        }
        int x = bot.getPosition().getX();
        int y = bot.getPosition().getY();
        if (y >= 9400) {
            sayUnderground(bot);
            return;
        }
        if (bot.getPosition().getPlane() == 0
                && x >= 2390 && x <= 2410
                && y >= 3094 && y <= 3114) {
            sayMid(bot);
            return;
        }
        if (GameUtil.randomInt(4) == 0) {
            sayFlagAttack(bot);
        } else {
            say(bot, GENERAL);
        }
    }

    public static void sayTeam(BotPlayer bot, CastleWarsManager.Team team) {
        if (team == CastleWarsManager.Team.SARADOMIN) {
            say(bot, SARADOMIN);
        } else if (team == CastleWarsManager.Team.ZAMORAK) {
            say(bot, ZAMORAK);
        }
    }

    public static void sayMid(BotPlayer bot) {
        say(bot, MID);
    }

    public static void sayCombat(BotPlayer bot) {
        say(bot, COMBAT);
    }

    public static void sayFlagAttack(BotPlayer bot) {
        say(bot, FLAG_ATTACK);
    }

    public static void sayFlagCarrier(BotPlayer bot) {
        say(bot, FLAG_CARRIER);
    }

    public static void sayDefence(BotPlayer bot) {
        say(bot, DEFENCE);
    }

    public static void sayUnderground(BotPlayer bot) {
        say(bot, UNDERGROUND);
    }

    public static void sayEngineering(BotPlayer bot) {
        say(bot, ENGINEERING);
    }

    public static void sayCatapult(BotPlayer bot) {
        say(bot, CATAPULT);
    }

    public static void sayScore(BotPlayer bot) {
        say(bot, SCORE);
    }

    private static void say(BotPlayer bot, String[] lines) {
        if (bot == null || lines == null || lines.length == 0) {
            return;
        }
        bot.queuePublicChatMessage(lines[GameUtil.randomInt(lines.length)]);
    }
}
