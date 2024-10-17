public class Queries{	
           public static final String insert_user="insert into users(name,mobile,mail) values(?,?,?)";
	public static final String insert_usercred="insert into usercred(id,username,password) values (?,?,?)";
	public static final String select_id="select id from users where mail=?";
           public static final String login_query = "SELECT id FROM usercred WHERE username = ? AND password = ?";
           public static final String insert_admin="insert into admin(name,mobile,mail,travels_name) values(?,?,?,?)";
           public static final String insert_admincred="insert into admincred(id,username,password) values(?,?,?)";
           public static final String select_adminid="select id from admin where mail=?";
           public static final String adminlogin_query = "SELECT id FROM admincred WHERE username = ? AND password = ?";
           public static final String admin_delete="delete from admin where mail=?";
           public static final String display_Route="select * from route";
           public static final String display_admin="select * from admin";
           public static final String display_bus = "SELECT bus.id,bus.bus_number AS Bus_Number, bus.seat_capacity AS Seat, bus.isac AS AC, bus.issleeper AS Sleeper, admin.travels_name AS Travels_Name, route.from_place AS Starting_Place, route.to_place AS Destination FROM bus JOIN route ON bus.route_id = route.id JOIN admin ON bus.admin_id = admin.id WHERE bus.admin_id = ?";
           public static final String admin_login="SELECT id FROM admincred WHERE username = ? AND password = ?";
           public static final String insert_bus="insert into bus(bus_number,seat_capacity,isac,issleeper,admin_id,route_id) values(?,?,?,?,?,?)";
}
