package com.rs2.model.skill.magic;

import com.rs2.ServerSettings;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.effect.BloodSpellHealEffect;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.DisarmWeaponEffect;
import com.rs2.model.combat.effect.MovementLockEffect;
import com.rs2.model.combat.effect.PoisonEffect;
import com.rs2.model.combat.effect.StatDrainEffect;
import com.rs2.model.combat.effect.SummonNpcCombatEffect;
import com.rs2.model.combat.effect.TargetRandomTeleportEffect;
import com.rs2.model.combat.effect.TeleblockEffect;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.item.ItemStack;

public enum SpellDefinition {
    WIND_STRIKE(1, 992, 711, GraphicEffect.createHeight100(90), 5.5, new ItemStack[]{new ItemStack(556), new ItemStack(558)}, SpellDefinition.createHitDefinition(2, 995, SpellDefinition.createProjectile(91), GraphicEffect.createHeight100(92)), true),
    WATER_STRIKE(5, 1023, 711, GraphicEffect.createHeight100(93), 7.5, new ItemStack[]{new ItemStack(556), new ItemStack(558), new ItemStack(555)}, SpellDefinition.createHitDefinition(4, 1022, SpellDefinition.createProjectile(94), GraphicEffect.createHeight100(95)), true),
    EARTH_STRIKE(9, 1002, 711, GraphicEffect.createHeight100(96), 9.5, new ItemStack[]{new ItemStack(556), new ItemStack(558), new ItemStack(557, 2)}, SpellDefinition.createHitDefinition(6, 1004, SpellDefinition.createProjectile(97), GraphicEffect.createHeight100(98)), true),
    FIRE_STRIKE(13, 1017, 711, GraphicEffect.createHeight100(99), 11.5, new ItemStack[]{new ItemStack(556, 2), new ItemStack(558), new ItemStack(554, 3)}, SpellDefinition.createHitDefinition(8, 1018, SpellDefinition.createProjectile(100), GraphicEffect.createHeight100(101)), true),
    WIND_BOLT(17, 1031, 711, GraphicEffect.createHeight100(117), 13.5, new ItemStack[]{new ItemStack(556, 2), new ItemStack(562)}, SpellDefinition.createHitDefinition(9, 1032, SpellDefinition.createProjectile(118), GraphicEffect.createHeight100(119)), true),
    WATER_BOLT(23, 1024, 711, GraphicEffect.createHeight100(120), 16.5, new ItemStack[]{new ItemStack(556, 2), new ItemStack(562), new ItemStack(555, 2)}, SpellDefinition.createHitDefinition(10, 1025, SpellDefinition.createProjectile(121), GraphicEffect.createHeight100(122)), true),
    EARTH_BOLT(29, 1003, 711, GraphicEffect.createHeight100(123), 19.5, new ItemStack[]{new ItemStack(556, 2), new ItemStack(562), new ItemStack(557, 3)}, SpellDefinition.createHitDefinition(11, 1006, SpellDefinition.createProjectile(124), GraphicEffect.createHeight100(125)), true),
    FIRE_BOLT(35, 1015, 711, GraphicEffect.createHeight100(126), 22.5, new ItemStack[]{new ItemStack(556, 3), new ItemStack(562), new ItemStack(554, 4)}, SpellDefinition.createHitDefinition(12, 1016, SpellDefinition.createProjectile(127), GraphicEffect.createHeight100(128)), true),
    WIND_BLAST(41, 1034, 711, GraphicEffect.createHeight100(132), 25.5, new ItemStack[]{new ItemStack(556, 3), new ItemStack(560)}, SpellDefinition.createHitDefinition(13, 1033, SpellDefinition.createProjectile(133), GraphicEffect.createHeight100(134)), true),
    WATER_BLAST(47, 1026, 711, GraphicEffect.createHeight100(135), 28.5, new ItemStack[]{new ItemStack(556, 3), new ItemStack(560), new ItemStack(555, 3)}, SpellDefinition.createHitDefinition(14, 1027, SpellDefinition.createProjectile(136), GraphicEffect.createHeight100(137)), true),
    EARTH_BLAST(53, 1007, 711, GraphicEffect.createHeight100(138), 31.5, new ItemStack[]{new ItemStack(556, 3), new ItemStack(560), new ItemStack(557, 4)}, SpellDefinition.createHitDefinition(15, 1005, SpellDefinition.createProjectile(139), GraphicEffect.createHeight100(140)), true),
    FIRE_BLAST(59, 1020, 711, GraphicEffect.createHeight100(129), 34.5, new ItemStack[]{new ItemStack(556, 4), new ItemStack(560), new ItemStack(554, 4)}, SpellDefinition.createHitDefinition(16, 1019, SpellDefinition.createProjectile(130), GraphicEffect.createHeight100(131)), true),
    WIND_WAVE(62, 1030, 711, GraphicEffect.createHeight100(158), 36.0, new ItemStack[]{new ItemStack(556, 5), new ItemStack(565)}, SpellDefinition.createHitDefinition(17, 1035, SpellDefinition.createProjectile(159), GraphicEffect.createHeight100(160)), true, true),
    WATER_WAVE(65, 1028, 711, GraphicEffect.createHeight100(161), 37.5, new ItemStack[]{new ItemStack(556, 5), new ItemStack(565), new ItemStack(555, 7)}, SpellDefinition.createHitDefinition(18, 1029, SpellDefinition.createProjectile(162), GraphicEffect.createHeight100(163)), true, true),
    EARTH_WAVE(70, 1009, 711, GraphicEffect.createHeight100(164), 40.0, new ItemStack[]{new ItemStack(556, 5), new ItemStack(565), new ItemStack(557, 7)}, SpellDefinition.createHitDefinition(19, 1008, SpellDefinition.createProjectile(165), GraphicEffect.createHeight100(166)), true, true),
    FIRE_WAVE(75, 1014, 711, GraphicEffect.createHeight100(155), 42.5, new ItemStack[]{new ItemStack(556, 5), new ItemStack(565), new ItemStack(554, 7)}, SpellDefinition.createHitDefinition(20, 1021, SpellDefinition.createProjectile(156), GraphicEffect.createHeight100(157)), true, true),
    SMOKE_RUSH(50, 1120, 1978, null, 30.0, new ItemStack[]{new ItemStack(562, 2), new ItemStack(560, 2), new ItemStack(554), new ItemStack(556)}, SpellDefinition.createHitDefinition(13, 1122, SpellDefinition.createProjectile(386), GraphicEffect.createHeight0(387)), true, null, (CombatEffect)new PoisonEffect(2.0), true),
    SHADOW_RUSH(52, 1115, 1978, null, 31.0, new ItemStack[]{new ItemStack(562, 2), new ItemStack(560, 2), new ItemStack(556), new ItemStack(566)}, SpellDefinition.createHitDefinition(14, 1114, SpellDefinition.createProjectile(378), GraphicEffect.createHeight0(379)), true, null, (CombatEffect)new StatDrainEffect(0, 0.1), true),
    BLOOD_RUSH(56, 986, 1978, null, 33.0, new ItemStack[]{new ItemStack(562, 2), new ItemStack(560, 2), new ItemStack(565)}, SpellDefinition.createHitDefinition(15, 989, SpellDefinition.createProjectile(372), GraphicEffect.createHeight0(373)), true, null, (CombatEffect)new BloodSpellHealEffect(0.4), true),
    ICE_RUSH(58, 1111, 1978, null, 34.0, new ItemStack[]{new ItemStack(562, 2), new ItemStack(560, 2), new ItemStack(555, 2)}, SpellDefinition.createHitDefinition(16, 1112, SpellDefinition.createProjectile(360), GraphicEffect.createHeight0(361)), true, null, (CombatEffect)new MovementLockEffect(8), true),
    SMOKE_BURST(62, 1120, 1979, null, 36.0, new ItemStack[]{new ItemStack(562, 4), new ItemStack(560, 2), new ItemStack(554, 2), new ItemStack(556, 2)}, SpellDefinition.createHitDefinition(17, 1124, SpellDefinition.createProjectile(-1), GraphicEffect.createHeight0(390)), true, null, (CombatEffect)new PoisonEffect(2.0), true),
    SHADOW_BURST(64, 1115, 1979, null, 37.0, new ItemStack[]{new ItemStack(562, 4), new ItemStack(560, 2), new ItemStack(556, 2), new ItemStack(566, 2)}, SpellDefinition.createHitDefinition(18, 1117, SpellDefinition.createProjectile(-1), GraphicEffect.createHeight0(382)), true, null, (CombatEffect)new StatDrainEffect(0, 0.1), true),
    BLOOD_BURST(68, 986, 1979, null, 39.0, new ItemStack[]{new ItemStack(562, 4), new ItemStack(560, 2), new ItemStack(565, 2)}, SpellDefinition.createHitDefinition(21, 984, SpellDefinition.createProjectile(-1), GraphicEffect.createHeight0(376)), true, null, (CombatEffect)new BloodSpellHealEffect(0.4), true),
    ICE_BURST(70, 1111, 1979, null, 40.0, new ItemStack[]{new ItemStack(562, 4), new ItemStack(560, 2), new ItemStack(555, 4)}, SpellDefinition.createHitDefinition(22, 1126, SpellDefinition.createProjectile(-1), GraphicEffect.createHeight0(363)), true, null, (CombatEffect)new MovementLockEffect(17), true),
    SMOKE_BLITZ(74, 1120, 1978, null, 42.0, new ItemStack[]{new ItemStack(560, 2), new ItemStack(565, 2), new ItemStack(554, 2), new ItemStack(556, 2)}, SpellDefinition.createHitDefinition(23, 1123, SpellDefinition.createProjectile(386), GraphicEffect.createHeight0(387)), true, null, (CombatEffect)new PoisonEffect(4.0), true),
    SHADOW_BLITZ(76, 1115, 1978, null, 43.0, new ItemStack[]{new ItemStack(560, 2), new ItemStack(565, 2), new ItemStack(556, 2), new ItemStack(566, 2)}, SpellDefinition.createHitDefinition(24, 1116, SpellDefinition.createProjectile(380), GraphicEffect.createHeight0(381)), true, null, (CombatEffect)new StatDrainEffect(0, 0.1), true),
    BLOOD_BLITZ(80, 986, 1978, null, 45.0, new ItemStack[]{new ItemStack(560, 2), new ItemStack(565, 4)}, SpellDefinition.createHitDefinition(25, 985, SpellDefinition.createProjectile(374), GraphicEffect.createHeight0(375)), true, null, (CombatEffect)new BloodSpellHealEffect(0.4), true),
    ICE_BLITZ(82, 1111, 1978, GraphicEffect.createHeight100(366), 48.5, new ItemStack[]{new ItemStack(560, 2), new ItemStack(565, 2), new ItemStack(555, 3)}, SpellDefinition.createHitDefinition(26, 1110, SpellDefinition.createProjectile(-1), GraphicEffect.createHeight0(367)).setDelay(3), true, null, (CombatEffect)new MovementLockEffect(25), true),
    SMOKE_BARRAGE(86, 1120, 1979, null, 48.0, new ItemStack[]{new ItemStack(560, 4), new ItemStack(565, 2), new ItemStack(554, 4), new ItemStack(556, 4)}, SpellDefinition.createHitDefinition(27, 1121, SpellDefinition.createProjectile(-1), GraphicEffect.createHeight0(391)), true, null, (CombatEffect)new PoisonEffect(4.0), true),
    SHADOW_BARRAGE(88, 1115, 1979, null, 49.0, new ItemStack[]{new ItemStack(560, 4), new ItemStack(565, 2), new ItemStack(556, 4), new ItemStack(566, 3)}, SpellDefinition.createHitDefinition(28, 1118, SpellDefinition.createProjectile(-1), GraphicEffect.createHeight0(383)), true, null, (CombatEffect)new StatDrainEffect(0, 0.15), true),
    BLOOD_BARRAGE(92, 986, 1979, null, 51.0, new ItemStack[]{new ItemStack(560, 4), new ItemStack(565, 4), new ItemStack(566)}, SpellDefinition.createHitDefinition(29, 1113, SpellDefinition.createProjectile(-1), GraphicEffect.createHeight0(377)), true, null, (CombatEffect)new BloodSpellHealEffect(0.4), true),
    ICE_BARRAGE(94, 1111, 1979, null, 52.0, new ItemStack[]{new ItemStack(560, 4), new ItemStack(565, 2), new ItemStack(555, 6)}, SpellDefinition.createHitDefinition(30, 1125, SpellDefinition.createProjectile(-1), GraphicEffect.createHeight0(369)), true, null, (CombatEffect)new MovementLockEffect(33), true),
    PADDEWWA_TELEPORT(54, -1, 1979, GraphicEffect.createHeight0(392), 64.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(554, 1), new ItemStack(556, 1)}, true),
    SENNTISTEN_TELEPORT(60, -1, 1979, GraphicEffect.createHeight0(392), 70.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(566, 1)}, true),
    KHARYRLL_TELEPORT(66, -1, 1979, GraphicEffect.createHeight0(392), 76.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(565, 1)}, true),
    LASSAR_TELEPORT(72, -1, 1979, GraphicEffect.createHeight0(392), 82.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(555, 4)}, true),
    DAREEYAK_TELEPORT(78, -1, 1979, GraphicEffect.createHeight0(392), 88.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(554, 3), new ItemStack(556, 2)}, true),
    CARRALLANGAR_TELEPORT(84, -1, 1979, GraphicEffect.createHeight0(392), 94.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(566, 2)}, true),
    ANNAKARL_TELEPORT(90, -1, 1979, GraphicEffect.createHeight0(392), 100.0, new ItemStack[]{new ItemStack(563, 1), new ItemStack(565, 1)}, true),
    GHORROCK_TELEPORT(96, -1, 1979, GraphicEffect.createHeight0(392), 106.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(555, 8)}, true),
    IBAN_BLAST(50, -1, 708, GraphicEffect.createHeight100(87), 42.0, new ItemStack[]{new ItemStack(554, 5), new ItemStack(560)}, SpellDefinition.createHitDefinition(25, -1, SpellDefinition.createProjectile(88), GraphicEffect.createHeight100(89)), true, true),
    CONFUSE(3, 180, 716, GraphicEffect.createHeight100(102), 13.0, new ItemStack[]{new ItemStack(555, 3), new ItemStack(557, 2), new ItemStack(559, 1)}, SpellDefinition.createHitDefinition(-1, -1, SpellDefinition.createProjectile(103), GraphicEffect.createHeight100(104)), true, (CombatEffect)new StatDrainEffect(0, 0.05), null),
    WEAKEN(11, 225, 716, GraphicEffect.createHeight100(105), 20.0, new ItemStack[]{new ItemStack(555, 3), new ItemStack(557, 2), new ItemStack(559, 1)}, SpellDefinition.createHitDefinition(-1, -1, SpellDefinition.createProjectile(106), GraphicEffect.createHeight100(107)), true, (CombatEffect)new StatDrainEffect(2, 0.05), null),
    CURSE(19, 1001, 717, GraphicEffect.createHeight100(108), 29.0, new ItemStack[]{new ItemStack(555, 2), new ItemStack(557, 3), new ItemStack(559, 1)}, SpellDefinition.createHitDefinition(-1, 1000, SpellDefinition.createProjectile(109), GraphicEffect.createHeight100(110)), true, (CombatEffect)new StatDrainEffect(1, 0.05), null),
    VULNERABILITY(66, -1, 729, GraphicEffect.createHeight100(167), 76.0, new ItemStack[]{new ItemStack(557, 5), new ItemStack(555, 5), new ItemStack(566, 1)}, SpellDefinition.createHitDefinition(-1, -1, SpellDefinition.createProjectile(168), GraphicEffect.createHeight100(169)), true, (CombatEffect)new StatDrainEffect(1, 0.1), null, true),
    ENFEEBLE(73, -1, 729, GraphicEffect.createHeight100(170), 83.0, new ItemStack[]{new ItemStack(557, 8), new ItemStack(555, 8), new ItemStack(566, 1)}, SpellDefinition.createHitDefinition(-1, -1, SpellDefinition.createProjectile(171), GraphicEffect.createHeight100(172)), true, (CombatEffect)new StatDrainEffect(2, 0.1), null, true),
    STUN(80, -1, 729, GraphicEffect.createHeight100(173), 90.0, new ItemStack[]{new ItemStack(557, 12), new ItemStack(555, 12), new ItemStack(566, 1)}, SpellDefinition.createHitDefinition(-1, -1, SpellDefinition.createProjectile(174), GraphicEffect.createHeight100(107)), true, (CombatEffect)new StatDrainEffect(0, 0.1), null, true),
    BIND(20, -1, 711, GraphicEffect.createHeight100(177), 30.0, new ItemStack[]{new ItemStack(557, 3), new ItemStack(555, 3), new ItemStack(561, 2)}, SpellDefinition.createHitDefinition(-1, -1, SpellDefinition.createProjectile(178), GraphicEffect.createHeight100(181)), true, (CombatEffect)new MovementLockEffect(8), null),
    SNARE(50, 1010, 711, GraphicEffect.createHeight100(177), 60.0, new ItemStack[]{new ItemStack(557, 4), new ItemStack(555, 4), new ItemStack(561, 3)}, SpellDefinition.createHitDefinition(2, 1011, SpellDefinition.createProjectile(178), GraphicEffect.createHeight100(180)), true, (CombatEffect)new MovementLockEffect(17), null, true),
    ENTANGLE(79, 1012, 711, GraphicEffect.createHeight100(177), 90.0, new ItemStack[]{new ItemStack(557, 5), new ItemStack(555, 5), new ItemStack(561, 4)}, SpellDefinition.createHitDefinition(5, 1013, SpellDefinition.createProjectile(178), GraphicEffect.createHeight100(179)), true, (CombatEffect)new MovementLockEffect(25), null, true),
    CRUMBLE_UNDEAD(39, -1, 724, GraphicEffect.createHeight100(146), 90.0, new ItemStack[]{new ItemStack(556, 2), new ItemStack(557, 2), new ItemStack(562, 1)}, SpellDefinition.createHitDefinition(15, -1, SpellDefinition.createProjectile(146), GraphicEffect.createHeight100(147)), true),
    MAGIC_DART(50, -1, 1576, GraphicEffect.createHeight100(327), 30.0, new ItemStack[]{new ItemStack(560, 1), new ItemStack(558, 4)}, SpellDefinition.createHitDefinition(10, -1, SpellDefinition.createProjectile(328), GraphicEffect.createHeight100(329)), true, true),
    SARADOMIN_STRIKE(50, -1, 811, null, 60.0, new ItemStack[]{new ItemStack(554, 2), new ItemStack(565, 2), new ItemStack(556, 4)}, SpellDefinition.createHitDefinition(20, -1, null, GraphicEffect.createHeight100(76)), true, true),
    CLAWS_OF_GUTHIX(60, -1, 811, null, 60.0, new ItemStack[]{new ItemStack(554, 1), new ItemStack(565, 2), new ItemStack(556, 4)}, SpellDefinition.createHitDefinition(20, -1, null, GraphicEffect.createHeight100(77)), true, true),
    FLAMES_OF_ZAMORAK(60, -1, 811, null, 60.0, new ItemStack[]{new ItemStack(554, 4), new ItemStack(565, 2), new ItemStack(556, 1)}, SpellDefinition.createHitDefinition(20, -1, null, GraphicEffect.createHeight0(78)), true, true),
    TELE_BLOCK(85, -1, 1819, null, 65.0, new ItemStack[]{new ItemStack(563, 1), new ItemStack(562, 1), new ItemStack(560, 1)}, SpellDefinition.createHitDefinition(-1, -1, SpellDefinition.createProjectile(344), GraphicEffect.createHeight0(345)), true, (CombatEffect)new TeleblockEffect(500), null, true),
    CHARGE(80, -1, 811, GraphicEffect.createHeight100(301), 0.0, new ItemStack[]{new ItemStack(554, 3), new ItemStack(565, 3), new ItemStack(556, 3)}, true),
    LOW_LEVEL_ALCHEMY(21, 224, 712, GraphicEffect.createHeight100(112), 31.0, new ItemStack[]{new ItemStack(554, 3), new ItemStack(561, 1)}),
    HIGH_LEVEL_ALCHEMY(55, 223, 713, GraphicEffect.createHeight100(113), 65.0, new ItemStack[]{new ItemStack(554, 5), new ItemStack(561, 1)}),
    TELEKINETIC_GRAB(33, -1, 728, GraphicEffect.createHeight100(142), 35.0, new ItemStack[]{new ItemStack(556, 1), new ItemStack(563, 1)}, SpellDefinition.createHitDefinition(-1, -1, SpellDefinition.createProjectile(143), new GraphicEffect(144, 0)), false),
    CHARGE_WATER_ORB(56, 214, 726, GraphicEffect.createHeight100(149), 66.0, new ItemStack[]{new ItemStack(555, 30), new ItemStack(564, 3), new ItemStack(567, 1)}, new ItemStack[]{new ItemStack(571, 1)}, true),
    CHARGE_EARTH_ORB(60, 213, 726, GraphicEffect.createHeight100(150), 70.0, new ItemStack[]{new ItemStack(557, 30), new ItemStack(564, 3), new ItemStack(567, 1)}, new ItemStack[]{new ItemStack(575, 1)}, true),
    CHARGE_FIRE_ORB(63, 216, 726, GraphicEffect.createHeight100(151), 73.0, new ItemStack[]{new ItemStack(554, 30), new ItemStack(564, 3), new ItemStack(567, 1)}, new ItemStack[]{new ItemStack(569, 1)}, true),
    CHARGE_AIR_ORB(66, 215, 726, GraphicEffect.createHeight100(152), 75.0, new ItemStack[]{new ItemStack(556, 30), new ItemStack(564, 3), new ItemStack(567, 1)}, new ItemStack[]{new ItemStack(573, 1)}, true),
    BONES_TO_BANANAS(15, -1, 722, GraphicEffect.createHeight100(141), 75.0, new ItemStack[]{new ItemStack(557, 2), new ItemStack(555, 2), new ItemStack(561)}),
    SUPERHEAT_ITEM(43, 217, 725, GraphicEffect.createHeight100(148), 53.0, new ItemStack[]{new ItemStack(554, 4), new ItemStack(561, 1)}),
    BONES_TO_PEACHES(60, -1, 722, GraphicEffect.createHeight100(311), 75.0, new ItemStack[]{new ItemStack(561, 2), new ItemStack(555, 4), new ItemStack(557, 4)}, true),
    LVL_1_ENCHANT(7, 212, 719, GraphicEffect.createHeight100(114), 18.0, new ItemStack[]{new ItemStack(555, 1), new ItemStack(564)}),
    LVL_2_ENCHANT(27, 209, 719, GraphicEffect.createHeight100(114), 37.0, new ItemStack[]{new ItemStack(556, 3), new ItemStack(564)}),
    LVL_3_ENCHANT(49, 208, 720, GraphicEffect.createHeight100(115), 59.0, new ItemStack[]{new ItemStack(554, 5), new ItemStack(564)}),
    LVL_4_ENCHANT(57, 207, 720, GraphicEffect.createHeight100(115), 67.0, new ItemStack[]{new ItemStack(557, 10), new ItemStack(564)}),
    LVL_5_ENCHANT(68, 206, 721, GraphicEffect.createHeight100(116), 78.0, new ItemStack[]{new ItemStack(557, 15), new ItemStack(555, 15), new ItemStack(564)}, true),
    LVL_6_ENCHANT(87, 205, 721, GraphicEffect.createHeight100(452), 97.0, new ItemStack[]{new ItemStack(557, 20), new ItemStack(554, 20), new ItemStack(564)}, true),
    TELEOTHER_LUMBRIDGE(74, -1, 1818, GraphicEffect.createHeight100(343), 0.0, new ItemStack[]{new ItemStack(566), new ItemStack(563), new ItemStack(557)}, true),
    TELEOTHER_FALADOR(82, -1, 1818, GraphicEffect.createHeight100(343), 0.0, new ItemStack[]{new ItemStack(566), new ItemStack(563), new ItemStack(555)}, true),
    TELEOTHER_CAMELOT(90, -1, 1818, GraphicEffect.createHeight100(343), 0.0, new ItemStack[]{new ItemStack(566, 2), new ItemStack(563)}, true),
    REANIMATE_GOBLIN(3, 1001, 716, GraphicEffect.createHeight100(108), 6.0, new ItemStack[]{new ItemStack(561, 1), new ItemStack(559, 2)}, true),
    REANIMATE_MONKEY(7, 1001, 716, GraphicEffect.createHeight100(108), 14.0, new ItemStack[]{new ItemStack(561, 1), new ItemStack(559, 3)}, true),
    REANIMATE_IMP(12, 1001, 716, GraphicEffect.createHeight100(108), 24.0, new ItemStack[]{new ItemStack(561, 2), new ItemStack(559, 3)}, true),
    REANIMATE_SCORPION(19, 1001, 716, GraphicEffect.createHeight100(108), 38.0, new ItemStack[]{new ItemStack(566, 1), new ItemStack(561, 1)}, true),
    REANIMATE_BEAR(21, 1001, 716, GraphicEffect.createHeight100(108), 42.0, new ItemStack[]{new ItemStack(566, 1), new ItemStack(561, 1), new ItemStack(559, 1)}, true),
    REANIMATE_UNICORN(22, 1001, 716, GraphicEffect.createHeight100(108), 44.0, new ItemStack[]{new ItemStack(566, 1), new ItemStack(561, 1), new ItemStack(559, 2)}, true),
    REANIMATE_DOG(26, 1001, 716, GraphicEffect.createHeight100(108), 52.0, new ItemStack[]{new ItemStack(566, 1), new ItemStack(561, 2), new ItemStack(559, 2)}, true),
    REANIMATE_CHAOS_DRUID(30, 1001, 716, GraphicEffect.createHeight100(108), 60.0, new ItemStack[]{new ItemStack(566, 1), new ItemStack(561, 2), new ItemStack(559, 3)}, true),
    REANIMATE_GIANT(37, 1001, 716, GraphicEffect.createHeight100(108), 74.0, new ItemStack[]{new ItemStack(566, 1), new ItemStack(561, 2), new ItemStack(559, 4)}, true),
    REANIMATE_OGRE(40, 1001, 716, GraphicEffect.createHeight100(108), 80.0, new ItemStack[]{new ItemStack(566, 1), new ItemStack(561, 3), new ItemStack(559, 4)}, true),
    REANIMATE_ELF(43, 1001, 716, GraphicEffect.createHeight100(108), 86.0, new ItemStack[]{new ItemStack(566, 2), new ItemStack(561, 2), new ItemStack(559, 2)}, true),
    REANIMATE_TROLL(46, 1001, 716, GraphicEffect.createHeight100(108), 92.0, new ItemStack[]{new ItemStack(566, 2), new ItemStack(561, 2), new ItemStack(559, 3)}, true),
    REANIMATE_KALPHITE(57, 1001, 716, GraphicEffect.createHeight100(108), 114.0, new ItemStack[]{new ItemStack(566, 2), new ItemStack(561, 3), new ItemStack(559, 4)}, true),
    REANIMATE_DAGANNOTH(62, 1001, 716, GraphicEffect.createHeight100(108), 124.0, new ItemStack[]{new ItemStack(566, 3), new ItemStack(561, 3), new ItemStack(559, 4)}, true),
    REANIMATE_BLOODVELD(65, 1001, 716, GraphicEffect.createHeight100(108), 130.0, new ItemStack[]{new ItemStack(566, 2), new ItemStack(565, 1), new ItemStack(561, 2)}, true),
    REANIMATE_TZHAAR(69, 1001, 716, GraphicEffect.createHeight100(108), 138.0, new ItemStack[]{new ItemStack(566, 2), new ItemStack(565, 1), new ItemStack(561, 3)}, true),
    REANIMATE_DEMON(72, 1001, 716, GraphicEffect.createHeight100(108), 144.0, new ItemStack[]{new ItemStack(566, 2), new ItemStack(565, 1), new ItemStack(561, 4)}, true),
    REANIMATE_ABYSSAL_CREATURE(85, 1001, 716, GraphicEffect.createHeight100(108), 170.0, new ItemStack[]{new ItemStack(566, 4), new ItemStack(565, 1), new ItemStack(561, 4)}, true),
    REANIMATE_DRAGON(93, 1001, 716, GraphicEffect.createHeight100(108), 186.0, new ItemStack[]{new ItemStack(566, 4), new ItemStack(565, 2), new ItemStack(561, 4)}, true),
    LUMBRIDGE_GRAVEYARD_TELEPORT(6, 202, 714, GraphicEffect.createHeight100(110), 10.0, new ItemStack[]{new ItemStack(563), new ItemStack(557, 2)}, true),
    DRAYNOR_MANOR_TELEPORT(17, 202, 714, GraphicEffect.createHeight100(110), 16.0, new ItemStack[]{new ItemStack(563), new ItemStack(557, 1), new ItemStack(555, 1)}, true),
    MIND_ALTAR_TELEPORT(28, 202, 714, GraphicEffect.createHeight100(110), 22.0, new ItemStack[]{new ItemStack(563), new ItemStack(558, 2)}, true),
    SALVE_GRAVEYARD_TELEPORT(40, 202, 714, GraphicEffect.createHeight100(110), 30.0, new ItemStack[]{new ItemStack(563), new ItemStack(566, 2)}, true),
    FENKENSTRAINS_CASTLE_TELEPORT(48, 202, 714, GraphicEffect.createHeight100(110), 50.0, new ItemStack[]{new ItemStack(563), new ItemStack(566, 1), new ItemStack(557, 1)}, true),
    WEST_ARDOUGNE_TELEPORT(61, 202, 714, GraphicEffect.createHeight100(110), 68.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(566, 2)}, true),
    CEMETERY_TELEPORT(71, 202, 714, GraphicEffect.createHeight100(110), 82.0, new ItemStack[]{new ItemStack(563), new ItemStack(566), new ItemStack(565)}, true),
    BARROWS_TELEPORT(83, 202, 714, GraphicEffect.createHeight100(110), 90.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(566, 2), new ItemStack(565)}, true),
    NECROMANCY_APE_ATOLL_TELEPORT(90, 202, 714, GraphicEffect.createHeight100(110), 100.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(566, 2), new ItemStack(565, 2)}, true),
    RESURRECT_CROPS(78, 170, 716, GraphicEffect.createHeight100(84), 90.0, new ItemStack[]{new ItemStack(566, 8), new ItemStack(561, 12), new ItemStack(565, 8), new ItemStack(557, 25)}, true),
    HOME_TELEPORT(0, 202, 714, GraphicEffect.createHeight100(301), 0.0, null),
    VARROCK_TELEPORT(25, 202, 714, GraphicEffect.createHeight100(301), 35.0, new ItemStack[]{new ItemStack(563), new ItemStack(556, 3), new ItemStack(554, 1)}),
    LUMBRIDGE_TELEPORT(31, 202, 714, GraphicEffect.createHeight100(301), 41.0, new ItemStack[]{new ItemStack(563), new ItemStack(556, 3), new ItemStack(557, 1)}),
    FALADOR_TELEPORT(37, 202, 714, GraphicEffect.createHeight100(301), 47.0, new ItemStack[]{new ItemStack(563), new ItemStack(556, 3), new ItemStack(555, 1)}),
    CAMELOT_TELEPORT(45, 202, 714, GraphicEffect.createHeight100(301), 55.0, new ItemStack[]{new ItemStack(563), new ItemStack(556, 5)}, true),
    ARDOUGNE_TELEPORT(51, 202, 714, GraphicEffect.createHeight100(301), 61.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(555, 2)}, true),
    WATCHTOWER_TELEPORT(58, 202, 714, GraphicEffect.createHeight100(301), 68.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(557, 2)}, true),
    TROLLHEIM_TELEPORT(61, 202, 714, GraphicEffect.createHeight100(301), 68.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(554, 2)}, true),
    APE_ATOLL_TELEPORT(64, 202, 714, GraphicEffect.createHeight100(301), 74.0, new ItemStack[]{new ItemStack(563, 2), new ItemStack(554, 2), new ItemStack(555, 2), new ItemStack(1963, 1)}, true),
    SPINOLYP_WATER_STRIKE(2868, SpellDefinition.createHitDefinition(10, -1, SpellDefinition.createProjectile(94), GraphicEffect.createHeight100(95)), true, (CombatEffect)new StatDrainEffect(5, 1)),
    WALLASALKI_WATER_WAVE(2365, SpellDefinition.createHitDefinition(25, -1, SpellDefinition.createProjectile(162), GraphicEffect.createHeight100(163)), true),
    DAGANNOTH_PRIME_WATER_WAVE(2853, SpellDefinition.createHitDefinition(61, -1, SpellDefinition.createProjectile(162), GraphicEffect.createHeight100(163)), true),
    SUMMON_ZOMBIE(811, SpellDefinition.createHitDefinition(0, -1, null, null), true, (CombatEffect)new SummonNpcCombatEffect(77)),
    MELZAR_CABBAGE_SPELL(711, GraphicEffect.createHeight100(177), SpellDefinition.createHitDefinition(0, -1, null, null), true),
    ABERRANT_SPECTER_MAGIC_ATTACK(1507, SpellDefinition.createHitDefinition(10, 878, SpellDefinition.createProjectile(336), null), true, 879),
    INFERNAL_MAGE_FIRE_BLAST(711, SpellDefinition.createHitDefinition(8, 1019, SpellDefinition.createProjectile(130), GraphicEffect.createHeight100(131)), true, 1020),
    CHAOS_ELEMENTAL_DISARM(3146, SpellDefinition.createHitDefinition(0, -1, SpellDefinition.createProjectile(551), GraphicEffect.createHeight100(550)), true, null, (CombatEffect)new DisarmWeaponEffect()),
    CHAOS_ELEMENTAL_RANDOM_TELEPORT(3146, SpellDefinition.createHitDefinition(0, -1, SpellDefinition.createProjectile(554), GraphicEffect.createHeight100(553)), true, null, (CombatEffect)new TargetRandomTeleportEffect()),
    JUNGLE_DEMON_WIND_WAVE(69, SpellDefinition.createHitDefinition(32, 1035, SpellDefinition.createProjectile(159), GraphicEffect.createHeight100(160)), true),
    JUNGLE_DEMON_WATER_WAVE(69, SpellDefinition.createHitDefinition(32, 1029, SpellDefinition.createProjectile(162), GraphicEffect.createHeight100(163)), true),
    JUNGLE_DEMON_EARTH_WAVE(69, SpellDefinition.createHitDefinition(32, 1008, SpellDefinition.createProjectile(165), GraphicEffect.createHeight100(166)), true),
    JUNGLE_DEMON_FIRE_WAVE(69, SpellDefinition.createHitDefinition(32, 1021, SpellDefinition.createProjectile(156), GraphicEffect.createHeight100(157)), true),
    ZOOKNOCK_WATER_BLAST(201, SpellDefinition.createHitDefinition(1, 1027, SpellDefinition.createProjectile(136), GraphicEffect.createHeight100(137)), true),
    FIRE_WIZARD_FIRE_STRIKE(13, 1017, 711, GraphicEffect.createHeight100(99), 11.5, new ItemStack[]{new ItemStack(556, 2), new ItemStack(558), new ItemStack(554, 3)}, SpellDefinition.createHitDefinition(6, 1018, SpellDefinition.createProjectile(100), GraphicEffect.createHeight100(101)), true),
    WIZARD_FIRE_STRIKE(13, 1017, 711, GraphicEffect.createHeight100(99), 11.5, new ItemStack[]{new ItemStack(556, 2), new ItemStack(558), new ItemStack(554, 3)}, SpellDefinition.createHitDefinition(4, 1018, SpellDefinition.createProjectile(100), GraphicEffect.createHeight100(101)), true),
    CHAOS_DRUID_CONFUSE(3, 180, 716, GraphicEffect.createHeight100(102), 13.0, new ItemStack[]{new ItemStack(555, 3), new ItemStack(557, 2), new ItemStack(559, 1)}, SpellDefinition.createHitDefinition(-1, -1, SpellDefinition.createProjectile(103), GraphicEffect.createHeight100(104)), true, (CombatEffect)new StatDrainEffect(0, 0.05), (CombatEffect)new MovementLockEffect(8)),
    GROWLER_MAGIC_ATTACK(7019, GraphicEffect.createHeight100(1184), SpellDefinition.createHitDefinition(16, -1, SpellDefinition.createProjectile(1185), GraphicEffect.createHeight100(1186)), true),
    COMMANDER_ZILYANA_MAGIC_ATTACK(6964, SpellDefinition.createHitDefinition(20, -1, SpellDefinition.createProjectile(-1), GraphicEffect.createHeight100(1194)), true),
    KRIL_TSUTSAROTH_MAGIC_ATTACK(6945, GraphicEffect.createHeight100(1210), SpellDefinition.createHitDefinition(30, -1, SpellDefinition.createProjectile(1211), GraphicEffect.createHeight100(-1)), true),
    BALFRUG_KREEYATH_MAGIC_ATTACK(7033, GraphicEffect.createHeight100(1212), SpellDefinition.createHitDefinition(16, -1, SpellDefinition.createProjectile(1213), GraphicEffect.createHeight100(-1)), true),
    KREE_ARRA_MAGIC_ATTACK(6977, SpellDefinition.createHitDefinition(21, -1, SpellDefinition.createProjectile(1196), GraphicEffect.createHeight100(1194)), true),
    SERGEANT_STEELWILL_MAGIC_ATTACK(6154, GraphicEffect.createHeight100(1202), SpellDefinition.createHitDefinition(16, -1, SpellDefinition.createProjectile(1203), GraphicEffect.createHeight100(1204)), true),
    ARMADYL_SPIRITUAL_MAGE_MAGIC_ATTACK(6952, SpellDefinition.createHitDefinition(16, -1, SpellDefinition.createProjectile(1192), GraphicEffect.createHeight100(-1)), true),
    WINGMAN_SKREE_MAGIC_ATTACK(6952, SpellDefinition.createHitDefinition(16, -1, SpellDefinition.createProjectile(1192), GraphicEffect.createHeight100(-1)), true);

    private int requiredLevel;
    private int animationId;
    private int castSoundId;
    private GraphicEffect castGraphic;
    private double experience;
    private ItemStack[] runeCosts;
    private ItemStack[] producedItems;
    private HitDefinition hitDefinition;
    private boolean combatSpell;
    private boolean membersOnly = false;
    private CombatEffect primaryEffect;
    private CombatEffect secondaryEffect;
    private CombatEffect postHitEffect;

    private SpellDefinition(int value3, HitDefinition hitDefinition, boolean enabled2, int value22) {
        this(0, value22, value3, null, 0.0, null, hitDefinition, true, null, null);
    }

    private SpellDefinition(int value2, HitDefinition hitDefinition, boolean enabled2) {
        this(0, -1, value2, null, 0.0, null, hitDefinition, true, null, null);
    }

    private SpellDefinition(int value2, GraphicEffect graphicEffect, HitDefinition hitDefinition, boolean enabled2) {
        this(0, -1, value2, graphicEffect, 0.0, null, hitDefinition, true, null, null);
    }

    private SpellDefinition(int value2, HitDefinition hitDefinition, boolean enabled2, CombatEffect combatEffect) {
        this(0, -1, value2, null, 0.0, null, hitDefinition, true, null, combatEffect);
    }

    private SpellDefinition(int value2, HitDefinition hitDefinition, boolean enabled2, CombatEffect combatEffect, CombatEffect combatEffect2) {
        this(0, -1, 3146, null, 0.0, null, hitDefinition, true, null, null, combatEffect2);
    }

    private SpellDefinition(int value5, int value22, int value32, GraphicEffect graphicEffect, double value6, ItemStack[] itemStackArray) {
        this(value5, value22, value32, graphicEffect, value6, itemStackArray, null, false, null, null);
    }

    private SpellDefinition(int value5, int value22, int value32, GraphicEffect graphicEffect, double value6, ItemStack[] itemStackArray, boolean enabled2) {
        this(value5, value22, value32, graphicEffect, value6, itemStackArray, null, false, true);
    }

    private SpellDefinition(int value5, int value22, int value32, GraphicEffect graphicEffect, double value6, ItemStack[] itemStackArray, ItemStack[] itemStackArray2, boolean enabled2) {
        this(value5, value22, 726, graphicEffect, value6, itemStackArray, null, false, itemStackArray2, true);
    }

    private SpellDefinition(int value5, int value22, int value32, GraphicEffect graphicEffect, double value6, ItemStack[] itemStackArray, HitDefinition hitDefinition, boolean enabled2) {
        this(value5, value22, value32, graphicEffect, value6, itemStackArray, hitDefinition, enabled2, null, null);
    }

    private SpellDefinition(int value5, int value22, int value32, GraphicEffect graphicEffect, double value6, ItemStack[] itemStackArray, HitDefinition hitDefinition, boolean enabled3, boolean enabled22) {
        this(value5, value22, value32, graphicEffect, value6, itemStackArray, hitDefinition, enabled3, null, null, enabled22);
    }

    private SpellDefinition(int value5, int value22, int value32, GraphicEffect graphicEffect, double value6, ItemStack[] itemStackArray, HitDefinition hitDefinition, boolean enabled3, ItemStack[] itemStackArray2, boolean enabled22) {
        this(value5, value22, value32, graphicEffect, value6, itemStackArray, null, false, null, null, itemStackArray2, enabled22);
    }

    private SpellDefinition(int value5, int value22, int animationId, GraphicEffect graphicEffect, double value6, ItemStack[] itemStackArray, HitDefinition hitDefinition, boolean combatSpell, CombatEffect combatEffect, CombatEffect combatEffect2, CombatEffect combatEffect3) {
        this.requiredLevel = 0;
        this.castSoundId = -1;
        this.animationId = animationId;
        this.castGraphic = null;
        this.experience = 0.0;
        this.runeCosts = null;
        this.hitDefinition = hitDefinition;
        this.combatSpell = combatSpell;
        this.primaryEffect = null;
        this.secondaryEffect = combatEffect2;
        this.postHitEffect = combatEffect3;
    }

    private SpellDefinition(int requiredLevel, int castSoundId, int animationId, GraphicEffect graphicEffect, double experience, ItemStack[] itemStackArray, HitDefinition hitDefinition, boolean combatSpell, CombatEffect combatEffect, CombatEffect combatEffect2) {
        this.requiredLevel = requiredLevel;
        this.castSoundId = castSoundId;
        this.animationId = animationId;
        this.castGraphic = graphicEffect;
        this.experience = experience;
        this.runeCosts = itemStackArray;
        this.hitDefinition = hitDefinition;
        this.combatSpell = combatSpell;
        this.primaryEffect = combatEffect;
        this.secondaryEffect = combatEffect2;
    }

    private SpellDefinition(int requiredLevel, int castSoundId, int animationId, GraphicEffect graphicEffect, double experience, ItemStack[] itemStackArray, HitDefinition hitDefinition, boolean combatSpell, CombatEffect combatEffect, CombatEffect combatEffect2, boolean membersOnly) {
        this.requiredLevel = requiredLevel;
        this.castSoundId = castSoundId;
        this.animationId = animationId;
        this.castGraphic = graphicEffect;
        this.experience = experience;
        this.runeCosts = itemStackArray;
        this.hitDefinition = hitDefinition;
        this.combatSpell = combatSpell;
        this.primaryEffect = combatEffect;
        this.secondaryEffect = combatEffect2;
        this.membersOnly = membersOnly;
    }

    private SpellDefinition(int requiredLevel, int castSoundId, int animationId, GraphicEffect graphicEffect, double experience, ItemStack[] itemStackArray, HitDefinition hitDefinition, boolean combatSpell, CombatEffect combatEffect, CombatEffect combatEffect2, ItemStack[] itemStackArray2, boolean membersOnly) {
        this.requiredLevel = requiredLevel;
        this.castSoundId = castSoundId;
        this.animationId = animationId;
        this.castGraphic = graphicEffect;
        this.experience = experience;
        this.runeCosts = itemStackArray;
        this.hitDefinition = hitDefinition;
        this.combatSpell = combatSpell;
        this.primaryEffect = null;
        this.secondaryEffect = null;
        this.producedItems = itemStackArray2;
        this.membersOnly = membersOnly;
    }

    public final boolean isCombatSpell() {
        return this.combatSpell;
    }

    public final boolean isMembersOnly() {
        return this.membersOnly;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int getAnimationId() {
        return this.animationId;
    }

    public final int getCastSoundId() {
        return this.castSoundId;
    }

    public final double getExperience() {
        return this.experience;
    }

    public final ItemStack[] getRuneCosts() {
        return this.runeCosts;
    }

    public final ItemStack[] getProducedItems() {
        return this.producedItems;
    }

    public final HitDefinition getHitDefinition() {
        return this.hitDefinition;
    }

    public final GraphicEffect getCastGraphic() {
        return this.castGraphic;
    }

    private static ProjectileDefinition createProjectile(int value2) {
        return new ProjectileDefinition(value2, ProjectileTiming.MAGIC);
    }

    private static HitDefinition createHitDefinition(int value4, int value22, ProjectileDefinition projectileDefinition, GraphicEffect graphicEffect) {
        int value3 = value4;
        value4 = 0;
        value4 = value3;
        return new HitDefinition(ServerSettings.MAGIC_ATTACK_STYLE, HitType.NORMAL, value4).setProjectile(projectileDefinition).setGraphic(graphicEffect).setDelay(0).setImpactSoundId(value22);
    }

    public final CombatEffect getPrimaryEffect() {
        return this.primaryEffect;
    }

    public final CombatEffect getSecondaryEffect() {
        return this.secondaryEffect;
    }

    public final CombatEffect getPostHitEffect() {
        return this.postHitEffect;
    }
}
