package com.rs2.model.skill.slayer;

import java.util.HashMap;
import java.util.Map;

public enum SlayerMonsterGuide {
    BANSHEE("banshee", new String[]{"Banshees are fearsome creatures with a mighty", "scream. You need something to cover your ears", "to protect from their scream. Banshees are", "are found in the Slayer Tower."}),
    BASILISK("basilisk", new String[]{"Basilisks are found where Fremmenik Slayers delve", "deep. Its stare is dangerous to any slayer. You", "will need something to deflect its stare", "along with a weapon that busts its armour."}),
    BAT("bat", new String[]{"Bats are typically weak creatures", "but are immune to magic attacks.", "However, they are weak to stabs. They", "are usually found outside of dungeons."}),
    BLOODVELD("bloodveld", new String[]{"Bloodvelds are somewhat weak creatures with a magical", "melee attack. These strange, demonic creatures", "are weak to various attack styles. They are", "found in the Slayer Tower north of Canifis."}),
    BLUE_DRAGON("blue dragon", new String[]{"These are the stronger cousin of the green dragon,", "and both require protection from their ferocious breath.", "Blue dragons are found in a few different dungeons", "across RuneScape. They are immune to magiplayer."}),
    BRONZE_DRAGON("bronze dragon", new String[]{"They may be the weakest in their family, but do not", "take them lightly. Like all dragons you need protection", "against their breath. They are weak to attacks that melt", "their metal and are found in Brimhaven Dungeon."}),
    CAVE_BUG("cave bug", new String[]{"Cave bugs can be quite dangerous and can be quite", "weak as well. They're weak to magic attacks and are", "sought for their excellent herb drops. You may find", "them under many swamps."}),
    CAVE_CRAWLER("cave crawler", new String[]{"These are nasty critters with a weakness", "to many weapons, especially those of Dorgeshuun", "descent.  Cave crawlers are found where Fremmenik", "Slayers go and also are found under swamps."}),
    CAVE_SLIME("cave slime", new String[]{"Cave slimes are weak overall but are", "poisonous if you are attacked. They are", "weak to magiplayer.  They are found under swamps."}),
    COCKATRICE("cockatrice", new String[]{"Cockatrices are weak monsters, especially when using", "a slash weapon.  You will need something to deflect", "their deadly beams from their eyes. They are found", "where many Fremmenik Slayers are."}),
    CRAWLING_HAND("crawling hand", new String[]{"Crawling hands are very weak creatures that derived", "from giant skeleton hands and were enchanted by Dark", "wizards. I would avoid high fiving these residents", "of the Slayer Tower."}),
    DAGANNOTH("dagannoth", new String[]{"These spiny creatures have either a", "range or melee attack. They are weak to", "weapons such as daggers. They fight in packs", "in island dungeons."}),
    LIZARD("lizard", new String[]{"These small creatures obviously live in", "the desert.  Their habitat, however, allows", "for them to stay alive unless they're cooled", "down somehow."}),
    DOG("dog", new String[]{"Dogs may be a pet, but are in fact", "vicious to all Slayers alike. Ones that", "need to be disposed of are found lurking", "in various dungeons."}),
    GHOUL("ghoul", new String[]{"Ghouls are vicious undead creatures", "found outside lurking in cemetaries. Their", "transparent skin is weak to stab attacks."}),
    GREEN_DRAGON("green dragon", new String[]{"Cousins of Elvarg, these dragons are not", "to be messed with.  Their ferocious breath", "means that protection will be needed. They", "are found mostly lurking in the Wilderness."}),
    EARTH_WARRIOR("earth warrior", new String[]{"These warriors of the Earth are weak to", "crush attacks.  They are only found in the", "dungeon of Edgeville, which is a tad bit", "in the Wilderness. So beware!"}),
    ELF("elf", new String[]{"Elves are quick, fierce warriors who are", "weak to melee attacks.  They are found guarding", "their homeland, which lies far West."}),
    HELLHOUND("hellhound", new String[]{"Their name says it all. These hounds of hell are natural", "devil spawn. They attack quickly and accurately, but", "high defense negates these attacks. Stab attacks weaken", "them. They are found closest to hell as they can get."}),
    HOBGOBLIN("hobgoblin", new String[]{"Not to be confused by their weaker cousin, Hobgoblins", "are tough goblins that are weak to all combat types.", "Their accuracy makes up for their weaknesses, They", "lurk all throughout RuneScape."}),
    ICE_GIANT("ice giant", new String[]{"These giants have a strong ice casing", "that is exceptionally weak to fire. They", "are found in below-freezing habitats."}),
    INFERNAL_MAGE("infernal mage", new String[]{"Infernal mages are masters of combat magiplayer.", "Range easily defeats these residents of the", "Slayer Tower."}),
    JELLY("jelly", new String[]{"Jellies are weak for their level and any", "melee weapon weakens them more. They're", "sought out for their clue scroll drops", "and are found in the Slayer Tower."}),
    KALPHITE("kalphite", new String[]{"Kalphites are nasty offspring of the", "Kalphite Queen.  Weaker ones are not", "poisonous but stronger ones are.  It is best", "to kill these with a crush weapon."}),
    LESSER_DEMON("lesser demon", new String[]{"Lesser demons are distant cousins to", "Greater demons, and as any demon are weak to", "Silverlight or Darklight. They can be found", "in many dungeons."}),
    MOSS_GIANT("moss giant", new String[]{"Moss Giants are cousins to the Ice Giant", "and are weak to fire spells. These are found", "in many dungeons and areas across RuneScape."}),
    MOGRE("mogre", new String[]{"Mogres are nasty river trolls who", "can be found at Mudskipper Point.", "Since trolls are so slow, range is", "their weakness."}),
    OGRE("ogre", new String[]{"Ogres are found far west in RuneScape.", "They are known to be slow and therefore", "range is their weakness."}),
    PYREFIEND("pyrefiend", new String[]{"Pyrefiends were born out of fire,", "and therefore water is their natural weakness.", "Pyrefiends are found where fire is most prevalent."}),
    ROCKSLUG("rockslug", new String[]{"Rockslugs are weak monsters which", "in order to defeat them you need some salt.", "They're found in under swamps and in some dungeons."}),
    SHADOW_WARRIOR("shadow warrior", new String[]{"Shadow warriors are masters of concealment.", "They're not seen on the mini map, and are weak", "to crush attacks. They exist in one dungeon only."}),
    SKELETON("skeleton", new String[]{"Skeletons are undead monsters and are", "weak to the spell Crumble Undead and crush", "attacks. They're found in dungeons all over", "RuneScape."}),
    TROLL("troll", new String[]{"Trolls are strong giants found throughout", "RuneScape, but mainly in Trollheim. They are", "weak to crush attacks and it you may want to", "have good defensive armour when fighting these."}),
    VAMPIRE("vampire", new String[]{"Vampires are unholy beings and are therefore", "weakened by a holy symbol. They do bite occasionally,", "but are overall weak creatures. They are found", "in themost haunted spots in RuneScape."}),
    WEREWOLF("werewolf", new String[]{"Werewolves are only found in Canifis", "and are weak to the Wolfbane dagger.", "They're very strong in werewolf form,", "but not in human form."}),
    ABERRANT_SPECTER("aberrant specter", new String[]{"Aberrant specters are odorous creatures", "and as a result require some sort of protection", "against their stench.  They are weak to slash", "attacks and are found in the Slayer Tower."}),
    FIRE_GIANT("fire giant", new String[]{"Fire giants were born from lava, and weak to", "ice attacks. They hit exceptionally well", "so it's advised to wear armour when fighting", "these. They are found in many dungeons."}),
    CROCODILE("crocodile", new String[]{"Crocodiles are desert creatures who", "reside near river banks to cool off.", "Their scaly armour proves weak to stab", "attacks."}),
    DUST_DEVIL("dust devil", new String[]{"Dust devils use clouds of dust, sand, ash and whatever", "else they can inhale to blind and disorientate their", "victims. Good luck on obtaining a dragon chainmail", "from their dusty remains."}),
    GOBLIN("goblin", new String[]{"Goblins are weak creatures found throughout", "RuneScape. They're weak to all combat styles,", "mainly melee."}),
    WALL_BEAST("wall beast", new String[]{"Wall beasts are vicious creatures who live within", "the walls of swampy caves. You will need some", "sort of helmet protection when fighting them", "or they will bash your head against the wall."}),
    TUROTH("turoth", new String[]{"Turoths are weak creatures that are only", "defeated by the Leaf-bladed weapon. They are", "found deep in the Fremmenik Slayer Dungeon."}),
    ABYSSAL_DEMON("abyssal demon", new String[]{"Abyssal demons are accurate monsters with the ability", "to teleport themselves and you. They're difficult", " to kill but are very rewarding,", "as they drop the Abyssal whip. These are found in", "the Slayer Tower."}),
    KURASK("kurask", new String[]{"Kurasks are similar to Turoths, but are", "much stronger. They are defeated by a Leaf-bladed", "weapon.  These are found in the Fremmenik Slayer", "dungeon."}),
    GREATER_DEMON("greater demon", new String[]{"Greater demons are accurate demons", "weak to Silverlight or Darklight.  It is", "recommended to wear heavy armour when fighting these."}),
    IRON_DRAGON("iron dragon", new String[]{"Iron dragons are the stronger version of", "the Bronze dragon, and are found near them too", "They are weak to fire spells, as this melts their", "armour."}),
    BLACK_DEMON("black demon", new String[]{"Black demons are the strongest demons", "in RuneScape. They are weak to Silverlight and", "Darklight but you will still need heavy armour", "to fend off their attacks."}),
    STEEL_DRAGON("steel dragon", new String[]{"Steel dragons are found where Bronze and Iron", "dragons are found.  They are stronger but are also", "weak to fire spells."}),
    GARGOYLE("gargoyle", new String[]{"Gargoyles are stone figures that are weak to", "crush attacks.  To defeat them, you need a rock", "hammer.  These are found in the Slayer Tower and", "are known for their Granite maul drops."}),
    SHADE("shade", new String[]{"Shades are weak to crush attacks and are", "of all levels.  They are accurate monsters", "and are found in the darkest parts of RuneScape."}),
    NECHRYAEL("nechryael", new String[]{"These demons are among the strongest demons", "in RuneScape. They are sought after for their", "rune boot drops.  Nechryaels are found in the", "Slayer Tower."});

    private String monsterName;
    private String[] guideTextLines;
    private static Map guidesByMonsterName;

    static {
        guidesByMonsterName = new HashMap();
        SlayerMonsterGuide[] slayerMonsterGuideArray = SlayerMonsterGuide.values();
        int length = slayerMonsterGuideArray.length;
        int index = 0;
        while (index < length) {
            SlayerMonsterGuide slayerMonsterGuide = slayerMonsterGuideArray[index];
            guidesByMonsterName.put(slayerMonsterGuide.monsterName, slayerMonsterGuide);
            ++index;
        }
    }
    private SlayerMonsterGuide(String monsterName, String[] guideTextLines) {
        this.monsterName = monsterName;
        this.guideTextLines = guideTextLines;
    }

    public static SlayerMonsterGuide forMonsterName(String text2) {
        return (SlayerMonsterGuide)((Object)guidesByMonsterName.get(text2));
    }

    public final String[] getGuideTextLines() {
        return this.guideTextLines;
    }
}

