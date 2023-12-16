package org.METROH.Audio_Player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.voice.AudioProvider;

public class Audio_Player_Provider {
	final AudioPlayerManager playerManager;
	final TrackScheduler scheduler;
	final AudioPlayer player;
	AudioProvider LavaPlayerAudioProvider;


	public Audio_Player_Provider(){
		// Creates AudioPlayer instances and translates URLs to AudioTrack instances
		playerManager = new DefaultAudioPlayerManager();

		// This is an optimization strategy that Discord4J can utilize.
		// It is not important to understand
		playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);

		// Allow playerManager to parse remote sources like YouTube links
		AudioSourceManagers.registerRemoteSources(playerManager);

		// Create an AudioPlayer so Discord4J can receive audio data
		player = playerManager.createPlayer();

		// We will be creating LavaPlayerAudioProvider in the next step
		LavaPlayerAudioProvider = new LavaPlayerAudioProvider(player);

		scheduler = new TrackScheduler(player);
	}

	public AudioPlayerManager getPlayerManager() {
		return playerManager;
	}

	public AudioPlayer getPlayer() {
		return player;
	}

	public AudioProvider getLavaPlayerAudioProvider() {
		return LavaPlayerAudioProvider;
	}

	public TrackScheduler getScheduler() {
		return scheduler;
	}
}
