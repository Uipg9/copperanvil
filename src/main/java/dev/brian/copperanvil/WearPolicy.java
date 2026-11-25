package dev.brian.copperanvil;

/** Pure wear rule for copper anvils: twice the vanilla damage chance, same three stages. */
public final class WearPolicy {
	/** Vanilla {@code AnvilMenu} chance that one use damages the anvil. */
	public static final float VANILLA_DAMAGE_CHANCE = 0.12f;
	public static final float DAMAGE_CHANCE = 0.24f;

	private WearPolicy() {
	}

	/** @param roll uniform random in [0, 1) */
	public static boolean shouldDamage(float roll) {
		return roll < DAMAGE_CHANCE;
	}

	public enum Stage {
		INTACT("copper_anvil"),
		CHIPPED("chipped_copper_anvil"),
		DAMAGED("damaged_copper_anvil");

		private final String path;

		Stage(String path) {
			this.path = path;
		}

		public String path() {
			return path;
		}

		/** Next wear stage, or {@code null} when the anvil breaks. */
		public Stage next() {
			return switch (this) {
				case INTACT -> CHIPPED;
				case CHIPPED -> DAMAGED;
				case DAMAGED -> null;
			};
		}
	}
}
