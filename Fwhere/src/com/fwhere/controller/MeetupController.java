package com.fwhere.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.fwhere.beans.Meetup;
import com.fwhere.service.MeetupService;

public class MeetupController extends MultiActionController {

	private MeetupService meetupService;
	
	public ModelAndView meetups(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("meetups.jsp");
		List<Meetup> meetups = new ArrayList<Meetup>();
		int outset = 1;
		int pageSize = 10;
		String pageNumStr = request.getParameter("pageNum");
		if(null != pageNumStr) {
			outset = Integer.parseInt(pageNumStr);
		}
		meetups = meetupService.getMeetups(outset,pageSize);
		mv.addObject("meetups", meetups);
		return mv;
	}

	public MeetupService getMeetupService() {
		return meetupService;
	}

	public void setMeetupService(MeetupService meetupService) {
		this.meetupService = meetupService;
	}


}
