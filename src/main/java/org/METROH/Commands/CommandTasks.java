package org.METROH.Commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import org.METROH.Audio_Player.Audio_Player_Provider;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class CommandTasks {
	private static final Audio_Player_Provider Audio_Player_Provider = new Audio_Player_Provider();

	public static Mono<Void> Ping_Response(MessageCreateEvent event) {
		return event.getMessage().getChannel().flatMap(channel -> channel.createMessage("Pong!")).then();
	}
	public static Mono<Void> Join_Channel(MessageCreateEvent event){

		return Mono.justOrEmpty(event.getMember())
				  .flatMap(Member::getVoiceState)
				  .flatMap(VoiceState::getChannel)
				  // join returns a VoiceConnection which would be required if we were
				  // adding disconnection features, but for now we are just ignoring it.
				  .flatMap(channel -> channel.join(spec -> spec.setProvider(Audio_Player_Provider.getLavaPlayerAudioProvider())))
				  .then();
	}
	public static Mono<Void> Play_Music(MessageCreateEvent event){
		return Mono.justOrEmpty(event.getMessage().getContent())
				  .map(content -> Arrays.asList(content.split(" ")))
				  .doOnNext(command -> Audio_Player_Provider.getPlayerManager().loadItem(command.get(1), Audio_Player_Provider.getScheduler()))
				  .then();
	}

	// Aquí puedes agregar más comandos...
}