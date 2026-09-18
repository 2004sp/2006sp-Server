package com.rs2.model.skill.magic;

import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.item.ItemService;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;

public final class ItemSpellAction
extends MagicSpellAction {
    private final SpellDefinition itemSpell;
    private final int itemId;
    private final Player caster;
    private final int inventorySlot;

    public ItemSpellAction(Player player, SpellDefinition spellDefinition, SpellDefinition spellDefinition2, int itemId, Player player2, int inventorySlot) {
        super(player, spellDefinition, (byte)0);
        this.itemSpell = spellDefinition2;
        this.itemId = itemId;
        this.caster = player2;
        this.inventorySlot = inventorySlot;
    }

    @Override
    public final boolean prepareCast() {
        switch (this.itemSpell) {
            case LVL_1_ENCHANT: {
                return this.castEnchantJewelry(this.itemId, 0);
            }
            case LVL_2_ENCHANT: {
                return this.castEnchantJewelry(this.itemId, 1);
            }
            case LVL_3_ENCHANT: {
                return this.castEnchantJewelry(this.itemId, 2);
            }
            case LVL_4_ENCHANT: {
                return this.castEnchantJewelry(this.itemId, 3);
            }
            case LVL_5_ENCHANT: {
                return this.castEnchantJewelry(this.itemId, 4);
            }
            case LVL_6_ENCHANT: {
                return this.castEnchantJewelry(this.itemId, 5);
            }
            case LOW_LEVEL_ALCHEMY: {
                ItemService.getInstance();
                int price = ItemService.getPrice(this.itemId, "lowalch", 995);
                return ItemSpellAction.castAlchemyItem(this.caster, this.itemId, this.inventorySlot, price, 1200, this.itemSpell);
            }
            case HIGH_LEVEL_ALCHEMY: {
                ItemService.getInstance();
                int price2 = ItemService.getPrice(this.itemId, "highalch", 995);
                return ItemSpellAction.castAlchemyItem(this.caster, this.itemId, this.inventorySlot, price2, 3000, this.itemSpell);
            }
            case SUPERHEAT_ITEM: {
                return this.castSuperheatItem(this.itemId);
            }
            case REANIMATE_GOBLIN: {
                return this.castNecromancyReanimation(this.itemId, 0);
            }
            case REANIMATE_MONKEY: {
                return this.castNecromancyReanimation(this.itemId, 1);
            }
            case REANIMATE_IMP: {
                return this.castNecromancyReanimation(this.itemId, 2);
            }
            case REANIMATE_SCORPION: {
                return this.castNecromancyReanimation(this.itemId, 3);
            }
            case REANIMATE_BEAR: {
                return this.castNecromancyReanimation(this.itemId, 4);
            }
            case REANIMATE_UNICORN: {
                return this.castNecromancyReanimation(this.itemId, 5);
            }
            case REANIMATE_DOG: {
                return this.castNecromancyReanimation(this.itemId, 6);
            }
            case REANIMATE_CHAOS_DRUID: {
                return this.castNecromancyReanimation(this.itemId, 7);
            }
            case REANIMATE_GIANT: {
                return this.castNecromancyReanimation(this.itemId, 8);
            }
            case REANIMATE_OGRE: {
                return this.castNecromancyReanimation(this.itemId, 9);
            }
            case REANIMATE_ELF: {
                return this.castNecromancyReanimation(this.itemId, 10);
            }
            case REANIMATE_TROLL: {
                return this.castNecromancyReanimation(this.itemId, 11);
            }
            case REANIMATE_KALPHITE: {
                return this.castNecromancyReanimation(this.itemId, 12);
            }
            case REANIMATE_DAGANNOTH: {
                return this.castNecromancyReanimation(this.itemId, 13);
            }
            case REANIMATE_BLOODVELD: {
                return this.castNecromancyReanimation(this.itemId, 14);
            }
            case REANIMATE_TZHAAR: {
                return this.castNecromancyReanimation(this.itemId, 15);
            }
            case REANIMATE_DEMON: {
                return this.castNecromancyReanimation(this.itemId, 16);
            }
            case REANIMATE_ABYSSAL_CREATURE: {
                return this.castNecromancyReanimation(this.itemId, 17);
            }
            case REANIMATE_DRAGON: {
                return this.castNecromancyReanimation(this.itemId, 18);
            }
        }
        return false;
    }

    @Override
    public final void applyImpact(HitDefinition hitDefinition) {
    }
}
