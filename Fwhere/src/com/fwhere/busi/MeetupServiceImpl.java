package com.fwhere.busi;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.fwhere.beans.Meetup;
import com.fwhere.service.MeetupService;

public class MeetupServiceImpl extends HibernateDaoSupport implements
		MeetupService {

	@SuppressWarnings("unchecked")
	public List<Meetup> getMeetups(final int outset, final int pageSize) {
		// TODO Auto-generated method stub
		final String hql = "from Meetup";
		List meetups = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setFirstResult((outset - 1) * pageSize);
						query.setMaxResults(10);
						List list = query.list();
						return list;
					}
				});
		return meetups;
	}

	public void addMeetups(Meetup meetup) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(meetup);
		return;
	}
}
