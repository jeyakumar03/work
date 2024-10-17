public class Bus{
	private int busnumber;
	private int seat;
	private boolean isAc;
	private boolean isSleeper;
	private int routeid;
	private int admin_id;
	Bus(int busnumber,int seat,boolean isAc,boolean isSleeper,int routeid,int admin_id){
		this.busnumber=busnumber;
		this.seat=seat;
		this.isAc=isAc;
		this.isSleeper=isSleeper;
		this.routeid=routeid;
		this.admin_id=admin_id;
	}
	public int getBusnum(){
		return busnumber;
	}
	public int getSeat(){
		return seat;
	}
	public boolean getAc(){
		return isAc;
	}
	public boolean getSleeper(){
		return isSleeper;
	}
	public int getRoute(){
		return routeid;
	}
	public int getAdmin(){
		return admin_id;
	}
	public String toString() {
        return "----------------------------------\n" +
               "Bus Number     : " + busnumber+ "\n" +
               "Seat Capacity  : " + seat + "\n" +
               "Ac             : " + isAc + "\n" +
               "Sleeper        : " + isSleeper + "\n" +
               "Route Id       : " + routeid + "\n" +
               "-----------------------------------\n";
    }
}
