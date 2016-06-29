package com.klouddata.dynamicview.entitytypes_data;

import java.io.Serializable;

public class Revision implements Serializable, Comparable {

	private static final long serialVersionUID = -5336457444558684074L;
	private int SERIAL_NO;
	private int REV;
	private int _ID_ISO;
	private int _ID;
	private int GRID_PK;
	private boolean is_Refreshed;

	public boolean is_Refreshed() {
		return is_Refreshed;
	}

	public void setIs_Refreshed(boolean is_Refreshed) {
		this.is_Refreshed = is_Refreshed;
	}

	public int getSERIAL_NO() {
		return SERIAL_NO;
	}

	public void setSERIAL_NO(int sERIAL_NO) {
		SERIAL_NO = sERIAL_NO;
	}

	public int getREV() {
		return REV;
	}

	public void setREV(int rEV) {
		REV = rEV;
	}

	public int get_ID_ISO() {
		return _ID_ISO;
	}

	public void set_ID_ISO(int iSO) {
		_ID_ISO = iSO;
	}

	public int get_ID() {
		return _ID;
	}

	public void set_ID(int _ID) {
		this._ID = _ID;
	}

	@Override
	public int compareTo(Object another) {
		return this.REV-((Revision) another).getREV();
	}

	public int getGRID_PK() {
		return GRID_PK;
	}

	public void setGRID_PK(int GRID_PK) {
		this.GRID_PK = GRID_PK;
	}
}
