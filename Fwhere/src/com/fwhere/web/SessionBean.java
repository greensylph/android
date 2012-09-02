package com.fwhere.web;

import java.util.Date;


public class SessionBean {
	
		private Long id;
	    private String no;
	    private Long orgId;
	    private Long staffId;
	    private String name;
	    private String password;
	    private String comment;
	    private Long deteler;
	    private Date deleted;
	    private Short deleteflag;
	    private String ime1;
	    private String ime2;
	    private Long creator;
	    private Date created;
	    private Long updater;
	    private Date updated;
	    private String accountId;
	    
	    /**glz add 11.03.07 09:33*/
	    private Date logTime;
	    
	    private Long sysId;
	    
	    private Long moduleId;
	    public Date getLogTime() {
			return logTime;
		}


		public void setLogTime(Date logTime) {
			this.logTime = logTime;
		}


		public Long getSysId() {
			return sysId;
		}


		public void setSysId(Long sysId) {
			this.sysId = sysId;
		}


		public Long getModuleId() {
			return moduleId;
		}


		public void setModuleId(Long moduleId) {
			this.moduleId = moduleId;
		}


		public Long getId() {
	        return id;
	    }


	    public void setId(Long id) {
	        this.id = id;
	    }


	    public String getNo() {
	        return no;
	    }


	    public void setNo(String no) {
	        this.no = no == null ? null : no.trim();
	    }


	    public Long getOrgId() {
	        return orgId;
	    }


	    public void setOrgId(Long orgId) {
	        this.orgId = orgId;
	    }


	    public Long getStaffId() {
	        return staffId;
	    }


	    public void setStaffId(Long staffId) {
	        this.staffId = staffId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password == null ? null : password.trim();
	    }

	    public String getComment() {
	        return comment;
	    }

	    public void setComment(String comment) {
	        this.comment = comment == null ? null : comment.trim();
	    }

	    public Long getDeteler() {
	        return deteler;
	    }

	    public void setDeteler(Long deteler) {
	        this.deteler = deteler;
	    }

	    public Date getDeleted() {
	        return deleted;
	    }

	    public void setDeleted(Date deleted) {
	        this.deleted = deleted;
	    }

	    public Short getDeleteflag() {
	        return deleteflag;
	    }

	    public void setDeleteflag(Short deleteflag) {
	        this.deleteflag = deleteflag;
	    }

	    public String getIme1() {
	        return ime1;
	    }

	    public void setIme1(String ime1) {
	        this.ime1 = ime1 == null ? null : ime1.trim();
	    }

	    public String getIme2() {
	        return ime2;
	    }

	    public void setIme2(String ime2) {
	        this.ime2 = ime2 == null ? null : ime2.trim();
	    }

	    public Long getCreator() {
	        return creator;
	    }

	    public void setCreator(Long creator) {
	        this.creator = creator;
	    }

	    public Date getCreated() {
	        return created;
	    }

	    public void setCreated(Date created) {
	        this.created = created;
	    }

	    public Long getUpdater() {
	        return updater;
	    }
	    public void setUpdater(Long updater) {
	        this.updater = updater;
	    }

	    public Date getUpdated() {
	        return updated;
	    }


	    public void setUpdated(Date updated) {
	        this.updated = updated;
	    }


		public String getAccountId() {
			return accountId;
		}


		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}
}
