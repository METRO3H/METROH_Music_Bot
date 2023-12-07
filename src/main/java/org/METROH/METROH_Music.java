package org.METROH;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class METROH_Music {

    interface Command {
        // Since we are expecting to do reactive things in this method, like
        // send a message, then this method will also return a reactive type.
        Mono<Void> execute(MessageCreateEvent event);
    }

    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("ping", event -> event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage("Pong!"))
                .then());
    }

    public static void main(String[] args){

        final GatewayDiscordClient client = DiscordClientBuilder.create(Constants.TOKEN).build()
                .login()
                .block();
        assert client != null;

        //Hay que habilitar la opción "MESSAGE CONTENT INTENT" en el portal de desarrollador
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .flatMap(event -> {

                    return Mono.just(event.getMessage().getContent())
                        .flatMap(content -> {
                            System.out.println("Content : '" + content + "'");

                            return  Flux.fromIterable(commands.entrySet())
                                    // We will be using ! as our "prefix" to any command in the system.
                                    .filter(entry -> content.startsWith('!' + entry.getKey()))
                                    .flatMap(entry -> entry.getValue().execute(event))
                                    .next();
                                }

                        );
                })
                .subscribe();

        client.onDisconnect().block();




    }



}
