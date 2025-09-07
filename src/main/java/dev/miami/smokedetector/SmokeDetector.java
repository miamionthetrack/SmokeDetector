package dev.miami.smokedetector;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class SmokeDetector implements ClientModInitializer {
	private static final int IntervalBetweenChirps = 5 * 60 * 20;
	private int tickCounter = 0;

	public static final Identifier chirp = Identifier.of("smokedetector", "chirp");
	public static final SoundEvent chirpsound = SoundEvent.of(chirp);

	@Override
	public void onInitializeClient() {
		Registry.register(Registries.SOUND_EVENT, chirp, chirpsound);
		ClientTickEvents.END_CLIENT_TICK.register(mc -> {
			if (mc.player == null || mc.world == null) return;

			tickCounter++;
			if (tickCounter >= IntervalBetweenChirps) {
				tickCounter = 0;

				mc.world.playSound(
						mc.player,
						mc.player.getBlockPos(),
						chirpsound,
						SoundCategory.PLAYERS,
						1.0F,
						1.0F
				);
			}
		});
	}
}
