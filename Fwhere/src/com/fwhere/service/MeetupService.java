package com.fwhere.service;

import java.util.List;

import com.fwhere.beans.Meetup;

public interface MeetupService {
	public List<Meetup> getMeetups(final int outset, final int pageSize);
	public void addMeetups(Meetup meetup);
}
