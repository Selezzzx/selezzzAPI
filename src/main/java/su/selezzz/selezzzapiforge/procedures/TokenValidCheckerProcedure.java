package su.selezzz.selezzzapiforge.procedures;

import su.selezzz.selezzzapiforge.network.StorytoolsModVariables;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

@Mod.EventBusSubscriber
public class TokenValidCheckerProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getEntity().level, event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		StorytoolsModVariables.MapVariables.get(world).isTokenActivated = false;
		StorytoolsModVariables.MapVariables.get(world).syncData(world);
		if (true) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal("§6§l[selezzz api] §rПроверка валидности токена..."), false);
			try {
				String tokInfo = HttpRequest.send("http://selezzzdev.pythonanywhere.com/get2/" + StorytoolsModVariables.MapVariables.get(world).token);
				String[] tokInfoPieces = tokInfo.split(";");
				if (Objects.equals(tokInfoPieces[1], "true")) {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal("§6§l[selezzz api] §aТокен успешно подключен!"), false);
					StorytoolsModVariables.MapVariables.get(world).isTokenActivated = true;
				} else {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal("§6§l[selezzz api] §4Токен не работает!"), false);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal("§6§l[selezzz api] §rДля пользования модом вам нужно установить токен (/loadtoken <юзернэйм> <токен>)"), false);

		}
	}
}
