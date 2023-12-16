package org.METROH.Commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public class CommandTasks {
	public static Mono<Void> pingResponse(MessageCreateEvent event) {
		return event.getMessage().getChannel().flatMap(channel -> channel.createMessage("Pong!")).then();
	}

	// Aquí puedes agregar más comandos...
}