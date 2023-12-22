package org.METROH;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.METROH.Commands.CommandList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class METROH_MUSIC {

	final GatewayDiscordClient client;

	METROH_MUSIC(String TOKEN){
		this.client = DiscordClientBuilder.create(TOKEN).build().login().block();
		assert this.client != null;
	}
	public void Connect(){
		this.client.getEventDispatcher().on(MessageCreateEvent.class).flatMap(event -> {

			return Mono.just(event.getMessage().getContent()).flatMap(content -> {

				this.printContent(content);

						  return Flux.fromIterable(CommandList.getMap().entrySet())
									 // We will be using ! as our "prefix" to any command in the system.
									 .filter(entry -> content.startsWith('!' + entry.getKey())).flatMap(entry -> entry.getValue().execute(event)).next();
					  }

			);
		}).subscribe();
	}
	public void On_Disconnect(){
		this.client.onDisconnect().block();
	}

	private void printContent(String content){
		System.out.println("Content : '" + content + "'");
	}

}
