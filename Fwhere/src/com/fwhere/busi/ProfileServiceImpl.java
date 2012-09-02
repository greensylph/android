package com.fwhere.busi;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.fwhere.beans.Profile;
import com.fwhere.service.ProfileService;

public class ProfileServiceImpl extends HibernateDaoSupport implements
		ProfileService {

	@SuppressWarnings("unchecked")
	public List<Profile> getProfile(int userid) throws Exception {
		return getHibernateTemplate().find("from Profile where userid=?",
				userid);
	}

	public void updateUserImg(final int userid, final String imgPath)
			throws Exception {
		// TODO Auto-generated method stub
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql = "update ORG_PROFILE SET img=:imgPath where userid=:userid";
				Query query = session.createSQLQuery(sql);
				query.setParameter("imgPath", imgPath);
				query.setParameter("userid", userid);
				return query.executeUpdate();
			}
		});
	}

}
