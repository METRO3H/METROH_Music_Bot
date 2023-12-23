package org.METROH.Audio_Player;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Scheduler implements AudioLoadResultHandler{

	private final AudioPlayer player;
	public Scheduler(final AudioPlayer player) {
		this.player = player;
	}
	@Override
	public void trackLoaded(final AudioTrack track) {
		// LavaPlayer found an audio source for us to play
		player.startTrack(track, true);
	}
	@Override
	public void playlistLoaded(final AudioPlaylist playlist) {
		// LavaPlayer found multiple AudioTracks from some playlist
	}

	@Override
	public void noMatches() {
		// LavaPlayer did not find any audio to extract
	}

	@Override
	public void loadFailed(final FriendlyException exception) {
		// LavaPlayer could not parse an audio source for some reason
	}
}
