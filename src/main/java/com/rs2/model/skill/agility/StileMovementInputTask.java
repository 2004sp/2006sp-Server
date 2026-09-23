package com.rs2.model.skill.agility;

// Intentionally left without a class. The former final-tick stile movement
// input experiment changed the server position before forced movement finished,
// which could let minimap paths be calculated from the wrong side of a fence.
// Stile input is now released only by AgilityMovementFinishTask at landing.
