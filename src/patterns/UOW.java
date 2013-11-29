package patterns;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import models.*;

import org.junit.Assert;

import database.JdbcUtilViaSSH;
import database.SSHjdbcSession;

import java.sql.Connection;
import java.sql.SQLException;


public class UOW {
	private List<DomainObject> newObjects = new ArrayList();
	private List<DomainObject> dirtyObjects = new ArrayList();
	private List<DomainObject> removedObjects = new ArrayList();
	private static UOW current = null;
	
	
	public static UOW getCurrent() {
		if (current == null)
		{
			current = new UOW();
		}
		
		return current;
	}
	
	
	public void registerNew(DomainObject obj) {
		Assert.assertNotNull("id not null", obj.getID());
		Assert.assertTrue("object not dirty", !dirtyObjects.contains(obj));
		Assert.assertTrue("object not removed", !removedObjects.contains(obj));
		Assert.assertTrue("object not already registered new", !newObjects.contains(obj));
		newObjects.add(obj);
	}
	
	public void registerDirty(DomainObject obj) {
		Assert.assertNotNull("id not null", obj.getID());
		Assert.assertTrue("object not removed", !removedObjects.contains(obj));
		if (!dirtyObjects.contains(obj) && !newObjects.contains(obj)) {
			dirtyObjects.add(obj);
		}
	}
	
	public void registerRemoved(DomainObject obj) {
		Assert.assertNotNull("id not null", obj.getID());
		if (newObjects.remove(obj)) return;
		dirtyObjects.remove(obj);
		if (!removedObjects.contains(obj)) {
			removedObjects.add(obj);
		}
	}
	
	public void registerClean(DomainObject obj) {
		Assert.assertNotNull("id not null", obj.getID());
	}
	
	public void commit() 
	{
		insertNew();
		updateDirty();
		deleteRemoved();
		newObjects.clear();
		dirtyObjects.clear();
		removedObjects.clear();
		GameMapper.getInstance().CleanAll();	
	}
	private void insertNew() {
		for (Iterator objects = newObjects.iterator(); objects.hasNext();) {
			DomainObject obj = (DomainObject) objects.next();
			// DB Insert
			GameMapper.getInstance().insert(obj);
		}
	}
	
	private void updateDirty() {
		for (Iterator objects = dirtyObjects.iterator(); objects.hasNext();) {
			DomainObject obj = (DomainObject) objects.next();
			// DB Update
			GameMapper.getInstance().update(obj);
		}
	}
	
	private void deleteRemoved() {
		for (Iterator objects = removedObjects.iterator(); objects.hasNext();) {
			DomainObject obj = (DomainObject) objects.next();
			// DB Delete
			GameMapper.getInstance().delete(obj.getID());
		}
	}
	public List<DomainObject> getAllNew() {
		return this.newObjects;
	}
	
	public void clear()
	{
		current = null;
	}

}
