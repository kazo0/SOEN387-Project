package models;

import java.sql.Connection;

import org.junit.Assert;

import patterns.UOW;

public abstract class DomainObject {

	
	private int ID;
	private DBStatus Status; 

	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		Assert.assertNotNull("Cannot set a null ID", ID);
		this.ID = ID;
	}
	public DomainObject(int ID) {
		this.ID = ID;
	}
	
	public DomainObject() {
	}
	
	public DomainObject(int id, DBStatus status) {
		this.ID = id;
		this.Status = status;
	}
	public void markNew() {
		this.setStatus(DBStatus.NEW);
		UOW.getCurrent().registerNew(this);
	}
	public void markClean() {
		this.setStatus(DBStatus.CLEAN);
		UOW.getCurrent().registerClean(this);
	}
	public void markDirty() {
		this.setStatus(DBStatus.DIRTY);
		UOW.getCurrent().registerDirty(this);
	}
	public void markRemoved() {
		this.setStatus(DBStatus.DELETED);
		UOW d = UOW.getCurrent();
		UOW.getCurrent().registerRemoved(this);
	}
	public DBStatus getStatus() {
		return Status;
	}
	public void setStatus(DBStatus status) {
		Status = status;
	}

}

