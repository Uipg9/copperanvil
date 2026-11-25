package dev.brian.copperanvil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CopperanvilTest {
	@Test
	void idUsesModNamespace() {
		var id = Copperanvil.id("example");
		assertEquals(Copperanvil.MOD_ID, id.getNamespace());
		assertEquals("example", id.getPath());
	}

	@Test
	void langKeyFollowsMinecraftConvention() {
		assertEquals("item.copperanvil.example", Copperanvil.langKey("item", "example"));
	}
}
