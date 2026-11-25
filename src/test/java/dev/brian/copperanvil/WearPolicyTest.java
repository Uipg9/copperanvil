package dev.brian.copperanvil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class WearPolicyTest {
	@Test
	void damageChanceIsTwiceVanilla() {
		assertEquals(0.24f, WearPolicy.DAMAGE_CHANCE);
		assertEquals(WearPolicy.VANILLA_DAMAGE_CHANCE * 2, WearPolicy.DAMAGE_CHANCE, 1e-6);
	}

	@Test
	void rollBelowChanceDamages() {
		assertTrue(WearPolicy.shouldDamage(0.0f));
		assertTrue(WearPolicy.shouldDamage(0.2399f));
		assertFalse(WearPolicy.shouldDamage(0.24f));
		assertFalse(WearPolicy.shouldDamage(0.99f));
	}

	@Test
	void stagesProgressThenBreak() {
		assertEquals(WearPolicy.Stage.CHIPPED, WearPolicy.Stage.INTACT.next());
		assertEquals(WearPolicy.Stage.DAMAGED, WearPolicy.Stage.CHIPPED.next());
		assertNull(WearPolicy.Stage.DAMAGED.next());
	}

	@Test
	void stageBlockIdsMatchRegistryPaths() {
		assertEquals("copper_anvil", WearPolicy.Stage.INTACT.path());
		assertEquals("chipped_copper_anvil", WearPolicy.Stage.CHIPPED.path());
		assertEquals("damaged_copper_anvil", WearPolicy.Stage.DAMAGED.path());
	}
}
