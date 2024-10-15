package Ticket;
public class Queries{
	public static final String insert_user="INSERT INTO users (name, mobile, mail) VALUES (?, ?, ?) ";
	public static final String insert_usercred="INSERT INTO usercred (id ,username, password) VALUES (?,?, ?)";
	public static final String select_id = "SELECT id FROM users WHERE mail = ?";
	public static final String add_theatre = "INSERT INTO theatre(name, location, capacity, isAc) VALUES(?, ?, ?, ?)";
           public static final String display_theatre = "SELECT theatre.name AS Theatre, theatre.location AS Location,movie.name AS Movie, theatre.isac AS Type FROM theatre JOIN movie ON movie.id = theatre.movie";  
	public static final String add_movie = "INSERT INTO movie(name, language, duration) VALUES(?, ?, ?)";
	public static final String display_movie= "SELECT * FROM movie";
	public static final String getMovieQuery = "SELECT id FROM movie WHERE id = ?";
           public static final String updateMovieQuery = "UPDATE theatre SET movie = ? WHERE id = ?";
           public static final String checkQuery = "SELECT id FROM theatre WHERE id = ?";
           public static final String deleteQuery = "DELETE FROM theatre WHERE id = ?";
           public static final String checkQuery_movie = "SELECT id FROM movie WHERE id = ?";
           public static final String deleteQuery_movie = "DELETE FROM movie WHERE id = ?";
           public static final String booking_query = "SELECT  theatre.name as TheatreName, movie.name as Movie ,theatre.location as Location, theatre.capacity as Capacity, theatre.isac as Type, movie.name as Movie FROM theatre JOIN movie ON theatre.movie = movie.id";
           public static final String displaytheatre = "SELECT theatre.id, theatre.name, theatre.capacity, theatre.location, theatre.isac " +"FROM theatre " +"JOIN movie ON theatre.movie = movie.id " +"WHERE movie.id = ? ";
           public static final String displayMoviesInTheatre = "SELECT movie.name as Movie, movie.language as Language, movie.duration as Duration " +"FROM movie JOIN theatre ON theatre.movie = movie.id " +"WHERE theatre.name = ?";
           public static final String movieQuery = "SELECT * FROM movie WHERE id = ?";
           public static final String theatreQuery = "SELECT capacity FROM theatre WHERE id = ? AND movie = ?";
           public static final String insertBookingQuery = "INSERT INTO booking (user_id, movie_id, theatre_id, tickets_booked, session_id, booking_date) VALUES (?, ?, ?, ?, ?, ?)";
           public static final String updateRemainingSeatsQuery = "UPDATE theatre SET remaining_seats = ? WHERE id = ? AND session_id = ?";
           public static final String query = "SELECT id FROM usercred WHERE username = ? AND password = ?";
           public static final String display_session="SELECT * from session";
           public static final String display_Bookings = "SELECT booking.id AS id,  movie.name AS movie_name,theatre.name AS theatre_name, booking.tickets_booked AS tickets_booked,session.name AS timing,booking.booking_date AS booking_date,theatre.location as theatre_location FROM booking JOIN movie ON booking.movie_id = movie.id JOIN theatre ON booking.theatre_id = theatre.id JOIN session ON booking.session_id = session.id WHERE booking.user_id = ? order by booking_date";
           public static final String login_query = "SELECT id FROM usercred WHERE username = ? AND password = ?";
           public static final String insert_admin="INSERT INTO admin (name, mobile, mail) VALUES (?, ?, ?) ";
           public static final String insert_admincred="INSERT INTO admincred (id ,username, password) VALUES (?,?, ?)";
      	public static final String select_adminid = "SELECT id FROM admin WHERE mail = ?";
      	public static final String delete_admin="DELETE FROM admin where mail=?";
      	public static final String seatCapacity="SELECT capacity from theatre where id=?";
      	public static final String display_movielocation= "SELECT * FROM theatre where location=?";
      	public static final String displaymovieonloctaion = "SELECT theatre.id, theatre.name, theatre.capacity, theatre.location, theatre.isac, movie.name as movie FROM theatre JOIN movie ON theatre.movie = movie.id WHERE theatre.location = ?";
           public static final String movie= "SELECT movie FROM theatre WHERE id=?";
           public static final String dis_theatre = "select * from theatre";
           public static final String display_admin="select * from admin";
           public static final String session = "SELECT * FROM session";
           public static final String choose_id = "SELECT booking.id AS id, " +
                   "movie.name AS movie_name, " +
                   "theatre.name AS theatre_name, " +
                   "booking.tickets_booked AS tickets_booked, " +
                   "session.name AS timing, " +
                   "booking.booking_date AS booking_date, " +
                   "theatre.location AS theatre_location " +
                   "FROM booking " +
                   "JOIN movie ON booking.movie_id = movie.id " +
                   "JOIN theatre ON booking.theatre_id = theatre.id " +
                   "JOIN session ON booking.session_id = session.id " +
                   "WHERE booking.user_id = ? AND booking.booking_date = CURRENT_DATE";  
      	public static final String current_seat_available="select sum(tickets_booked) FROM booking where theatre_id=? and session_id=? and booking_date=(select current_date) group by session_id,booking_date order by session_id";          
}
