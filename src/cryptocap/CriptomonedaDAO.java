package cryptocap;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.*;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;


/**
 * CriptomonedaDAO.java
 * Operaciones CRUD sobre el TDA Criptomoneda.
 * 
 * 
 * 
 * 
 * CREATE TABLE criptomonedas (
 *  acronimo varchar(10) NOT NULL,
 *  nombre varchar(128) NOT NULL,
 *  urlDatos varchar(128),
 *  ultAct varchar(128) NOT NULL,
 *  precio varchar(128),
 *  capitalizacion varchar(128),
 *  vol24 varchar(128),
 *  volTotal varchar(128),
 *  lastdaychange varchar(128),
 *  sevendaychange varchar(128),
 *  PRIMARY KEY (acronimo),
 *  UNIQUE KEY acronimo_UNIQUE (acronimo)
 * ) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
 *
 *
 *
 */

public class CriptomonedaDAO 
{	
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection = null;
    
	String acronimo;
	String nombre;
	String imagen;
	String urlDatos;
	String ultAct;
	float precio;
	String capitalizacion;
	String vol24;
	String volTotal;
	String lastdaychange;
	String sevendaychange;
	String status;
	float total_volume_24h_reported;
	float total_volume_24h;
	float total_market_cap;
	
    
    public CriptomonedaDAO(String url, String user, String password){
        this.jdbcURL = url;
        this.jdbcUsername = user;  
        this.jdbcPassword = password;
    }

    protected void connect() throws SQLException {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	        jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

    

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()){
            jdbcConnection.close();
        }
    }
    
    public List<Criptomoneda> list() throws SQLException{
    	List<Criptomoneda> c = new ArrayList<>();
		String sql = "SELECT * FROM criptomonedas";
		PreparedStatement st;
		ResultSet rs;
		
    	connect();
    	
		st = jdbcConnection.prepareStatement(sql);
		rs = st.executeQuery();
		
		while(rs.next()) {
			nombre = rs.getString(2);
	    	acronimo = rs.getString(1);
	    	imagen = rs.getString(11);
	    	urlDatos = rs.getString(3);
	    	precio = rs.getFloat(5);
	    	capitalizacion = rs.getString(6);
	    	vol24 = rs.getString(7);
	    	volTotal = rs.getString(8); 
	    	lastdaychange = rs.getString(9);
	    	sevendaychange = rs.getString(10);
	    	ultAct = rs.getString(4);
			c.add(new Criptomoneda(nombre, acronimo, imagen, urlDatos, precio, capitalizacion, vol24, volTotal, lastdaychange, sevendaychange, ultAct));
		}
		
		rs.close();
		st.close();
    	
		disconnect();
		return c;
    	
    }
    
    public List<Criptomoneda> listMarket(String status) throws SQLException{
    	List<Criptomoneda> c = new ArrayList<>();
		String sql = "SELECT * FROM currency where status = ?";
		PreparedStatement st;
		ResultSet rs;
		
    	connect();
    	
		st = jdbcConnection.prepareStatement(sql);
		st.setString(1, status);
		rs = st.executeQuery();
		
		while(rs.next()) {
			nombre = rs.getString(2);
	    	acronimo = rs.getString(1);
	    	imagen = rs.getString(11);
	    	urlDatos = rs.getString(3);
	    	precio = rs.getFloat(5);
	    	total_market_cap = rs.getFloat(12);
	    	total_volume_24h = rs.getFloat(13);
	    	total_volume_24h_reported = rs.getFloat(14);
	    	vol24 = rs.getString(7);
	    	volTotal = rs.getString(8); 
	    	lastdaychange = rs.getString(9);
	    	sevendaychange = rs.getString(10);
	    	ultAct = rs.getString(4);
			c.add(new Criptomoneda(nombre, acronimo, imagen, urlDatos, precio, capitalizacion, vol24, volTotal, lastdaychange, sevendaychange, ultAct, total_market_cap, total_volume_24h, total_volume_24h_reported));
		}
		
		rs.close();
		st.close();
    	
		disconnect();
		return c;
    	
    }
    
    public ArrayList<Criptomoneda> listCurrency(String status) throws SQLException {
    	ArrayList<Criptomoneda> res = new ArrayList<>();
    	String sql;
		PreparedStatement st;
		ResultSet rs;
		
		connect();
		
		switch(status) {
			case "disabled":
				sql = "SELECT * FROM currency";
				sql += " where status = ?";
				st = jdbcConnection.prepareStatement(sql);
				st.setString(1, "disabled");
				break;
			case "enabled":
				sql = "SELECT * FROM currency";
				sql += " where status = ?";
				st = jdbcConnection.prepareStatement(sql);
				st.setString(1, "enabled");
				break;
			default:
				sql = "SELECT * FROM currency";
				st = jdbcConnection.prepareStatement(sql);
				break;
		}
	
		
		rs = st.executeQuery();
		
		while(rs.next()) {
			res.add(new Criptomoneda(rs.getString(1), rs.getString(2)));
		}
		
		rs.close();
		st.close();
		
		disconnect();
	
		return res;
    }
    
    public List<String> getListing() throws SQLException{
    	List<String> res = new ArrayList<>();
    	String sql = "SELECT * FROM currency";
    	PreparedStatement st;
		ResultSet rs;
		
    	connect();
    	
		st = jdbcConnection.prepareStatement(sql);
		rs = st.executeQuery();
		
		while(rs.next()) {
			res.add(rs.getString(1));
		}
		
		rs.close();
		st.close();
    	
		disconnect();
		return res;
    }
    
	public Criptomoneda getCriptomoneda(String acron) throws SQLException{
		String sql;
		sql = "SELECT * FROM criptomonedas";
		sql += " WHERE acronimo = ?";
		PreparedStatement st;
		ResultSet rs;

		connect();

		st = jdbcConnection.prepareStatement(sql);
		st.setString(1, acron);
		
		rs = st.executeQuery();
		if(rs.next()) {
			nombre = rs.getString(2);
	    	acronimo = rs.getString(1);
	    	imagen = rs.getString(11);
	    	urlDatos = rs.getString(3);
	    	precio = rs.getFloat(5);
	    	capitalizacion = rs.getString(6);
	    	vol24 = rs.getString(7);
	    	volTotal = rs.getString(8); 
	    	lastdaychange = rs.getString(9);
	    	sevendaychange = rs.getString(10);
	    	ultAct = rs.getString(4);
			
			rs.close();
			st.close();

			return (new Criptomoneda(nombre, acronimo, imagen, urlDatos, precio, capitalizacion, vol24, volTotal, lastdaychange, sevendaychange, ultAct));				
		}
		rs.close();
		st.close();
		disconnect();
    	return (new Criptomoneda("Error"));
	}
    
    public boolean addCurrency(String acron) throws SQLException {
		String sql;
    	PreparedStatement st;
    	ResultSet rs;
    	boolean stat = false;
    	
    	connect();
    	
    	sql = "SELECT * FROM currency";
		sql += " WHERE acronimo = ?";

		st = jdbcConnection.prepareStatement(sql);
		st.setString(1, acron);
		
		rs = st.executeQuery();
		// si FALSE --> INSERT
		if(!rs.next()) {
			sql = "INSERT INTO currency (acronimo)";
			sql += " VALUES (?)";
			st = jdbcConnection.prepareStatement(sql);
			st.setString(1, acron);
			stat = st.executeUpdate() > 0;
			st.close();
			
		} 
		
		st.close();
		disconnect();
		
		return stat;
    }
    
	public List<HistorialPrecio> getHistory(String acron) throws SQLException {
		List<HistorialPrecio> history = new ArrayList<>();
		String sql;
		sql = "SELECT * FROM history";
		sql += " WHERE acronimo = ?";
		PreparedStatement st;
		ResultSet rs;

		connect();

		st = jdbcConnection.prepareStatement(sql);
		st.setString(1, acron);
		
		rs = st.executeQuery();
		while(rs.next()) {
			ultAct = rs.getString(1);
	    	acronimo = rs.getString(2);
	    	precio = rs.getFloat(3);
	    	
	    	history.add(new HistorialPrecio(ultAct, acronimo, precio));
		}
		
		disconnect();
		return history;
	}
    
    public boolean addToHistory(String acron, float precio) throws SQLException {
		String sql;
    	PreparedStatement st;
    	ResultSet rs;
    	boolean stat = false;
    	
    	sql = "INSERT INTO history (fecha, acronimo, precio)";
		sql += " VALUES (?, ?, ?)";
		st = jdbcConnection.prepareStatement(sql);
		
		st.setString(1, getActualHour());
		st.setString(2, acron);
		st.setFloat(3, precio);
		stat = st.executeUpdate() > 0;
		st.close();
			
		
		return stat;
    }
    
    
    public boolean remove(String acron, String entity) throws SQLException{
    	String sql;
    	PreparedStatement st;
    	boolean stat = false;
    	
    	connect();
    	
    	switch(entity) {
    		case "criptomoneda":
    			sql = "DELETE FROM criptomonedas where acronimo = ?";
    	    	st = jdbcConnection.prepareStatement(sql);
    	    	st.setString(1, acron);
    	    	stat = st.executeUpdate() > 0;
    			stat = setCurrencyStatus(acron, "disabled");
    	    	
    	    	st.close();
    			break;
    		case "currency":
    			sql = "DELETE FROM currency where acronimo = ?";
    	    	st = jdbcConnection.prepareStatement(sql);
    	    	st.setString(1, acron);
    	    	stat = st.executeUpdate() > 0;
    	    	
    	    	st.close();
    			
    	    	break;
    	}

		disconnect();
		
		return stat;
    	
    }
    
    
    public boolean setCurrencyStatus(String acronimo, String status) throws SQLException {
    	String sql;
    	PreparedStatement st;
    	boolean stat = false;
    	
    	
    	sql = "UPDATE currency SET"; 
		sql += " status = ? where acronimo = ?";
		
		st = jdbcConnection.prepareStatement(sql);
		st.setString(1, status);
		st.setString(2, acronimo);
    	
		stat = st.executeUpdate() > 0;
    	
    	
		return stat;
    	
    }
    
    public List<Criptomoneda> investing () throws IOException, SQLException{
    	List<String> lista = getListing();
    	List<Criptomoneda> criptos = new ArrayList<>();
    	Criptomoneda crip;
    	Webscraping it;
		String sql;
		PreparedStatement st;
		ResultSet rs;
    	boolean stat = false;
		
		connect();
    	
		// lista de criptomonedas recopilada en la tabla "currency"
		// status: enabled (aquellas encontradas)
		//		   disabled (no se ha encontrado)
        for(int i=0; i<lista.size(); i++){
        	it = new Webscraping();
        	
        	// scrapeamos --> Class Webscraping
        	crip = it.Investing(lista.get(i));
        	if(crip.getStatus().equals("enabled")) {
        		criptos.add(crip);
        		       		
        		acronimo = crip.getAcronimo();
            	nombre = crip.getNombre();
            	precio = crip.getPrecio();
            	capitalizacion = crip.getCapMercado();
            	vol24 = crip.getVolumen24();
            	volTotal = crip.getVolumenTotal(); 
            	lastdaychange = crip.getVariacion24();
            	sevendaychange = crip.getVariacion7();
            	ultAct = crip.getUltimaActualizacion();
            	
            	// necesitamos saber si existe en la base de datos (2 casos)
            	sql = "SELECT * FROM criptomonedas";
        		sql += " WHERE acronimo = ?";

        		st = jdbcConnection.prepareStatement(sql);
        		st.setString(1, acronimo);
            	
        		rs = st.executeQuery();
        		
        		// INSERCION
        		if(!rs.next()) {
        			sql = "INSERT INTO criptomonedas (acronimo, nombre, ultAct, precio, capitalizacion, vol24, volTotal, lastdaychange, sevendaychange)";
        			sql += " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        			
        			st = jdbcConnection.prepareStatement(sql);
        			st.setString(1, acronimo);
        			st.setString(2, nombre);
        			st.setString(3, ultAct);
        			st.setFloat(4, precio);
        			st.setString(5, capitalizacion);
        			st.setString(6, vol24);
        			st.setString(7, volTotal);
        			st.setString(8, lastdaychange);
        			st.setString(9, sevendaychange);
        			stat = st.executeUpdate() > 0;
        			stat = addToHistory(acronimo, precio);
    	            stat = setCurrencyStatus(acronimo, "enabled");

        			st.close();
        			
        		} else {            	
        			// MODIFICACI�N
        			sql = "UPDATE criptomonedas SET"; 
        			sql += " nombre = ?, ultAct = ?, precio = ?, capitalizacion = ?, vol24 = ?, volTotal = ?, lastdaychange = ?, sevendaychange = ? where acronimo = ?";
        			
        			st = jdbcConnection.prepareStatement(sql);
        			st.setString(1, nombre);
        			st.setString(2, ultAct);
        			st.setFloat(3, precio);
        			st.setString(4, capitalizacion);
        			st.setString(5, vol24);
        			st.setString(6, volTotal);
        			st.setString(7, lastdaychange);
        			st.setString(8, sevendaychange);
        			st.setString(9, acronimo);
        			
        			stat = st.executeUpdate() > 0;
        			stat = addToHistory(acronimo, precio);
        			
        			st.close();
        			
        		} 
        	} else {
        		stat = setCurrencyStatus(lista.get(i), "disabled");
        	}

        }

        disconnect();

        return criptos;
    }
    
    
    
	public List<Criptomoneda> coinranking() throws SQLException, IOException, URISyntaxException, ClassNotFoundException {
    	List<String> lista = getListing();
    	List<Criptomoneda> criptos = new ArrayList<>();
    	Criptomoneda crip;
        Webscraping it;
		String sql;
		PreparedStatement st;
		ResultSet rs;
		boolean stat;
		
		
    	connect();
    	
        // Recorremos la lista de criptomonedas
        for(int i=0; i<lista.size(); i++){
        	it = new Webscraping();
        	
        	// scrapeamos --> Class Webscraping
        	crip = it.Coinranking(lista.get(i));
        	if(crip.getStatus().equals("enabled")) {
        		criptos.add(crip);
            
	            nombre = crip.getNombre();
	            status = crip.getStatus();
	            acronimo = crip.getAcronimo();
	            urlDatos = crip.getUrlDatos();
	            ultAct = crip.getUltimaActualizacion();
	            imagen = crip.getImagen();
	            
	            
	            // QUERY1: �Existe la moneda "i"?
	    		sql = "SELECT * FROM criptomonedas";
	    		sql += " WHERE acronimo = ?";
	    	
	    		st = jdbcConnection.prepareStatement(sql);
	    		st.setString(1, acronimo);
	        	
	    		rs = st.executeQuery();
	    		// si FALSE --> INSERT
	    		if(!rs.next() ) {
					sql = "INSERT INTO criptomonedas (acronimo, nombre, urlDatos, ultAct, urlImagen)";
					sql += " VALUES (?, ?, ?, ?, ?)";
					
					st = jdbcConnection.prepareStatement(sql);
					st.setString(1, acronimo);
					st.setString(2, nombre);
					st.setString(3, urlDatos);
					st.setString(4, ultAct);
					st.setString(5, imagen);
					
					stat = st.executeUpdate() > 0;
		            stat = setCurrencyStatus(acronimo, "enabled");
		            st.close();
	    			
	    		} else {
	    			sql = "UPDATE criptomonedas SET"; 
	    			sql += " nombre = ?, urlDatos = ?, ultAct = ?, urlImagen = ? where acronimo = ?";
	    			
	    			st = jdbcConnection.prepareStatement(sql);
	    			st.setString(1, nombre);
	    			st.setString(2, urlDatos);
	    			st.setString(3, ultAct);
	       			st.setString(4, imagen);
	    			st.setString(5, acronimo);
	    			
	    			stat = st.executeUpdate() > 0;
	    			st.close();
	    			
	    		} 
    	
        	} else {
        		stat = setCurrencyStatus(lista.get(i), "disabled");
        	}
        }

        disconnect();

        return criptos;
    }
	
	public List<Criptomoneda> refreshMarket() throws SQLException {
		List<String> lista = getListing();
    	List<Criptomoneda> criptos = new ArrayList<>();
    	Criptomoneda crip;
        Webscraping it;
		String sql;
		PreparedStatement st;
		ResultSet rs;
		boolean stat;
		
		
    	connect();
    	
        // Recorremos la lista de criptomonedas
        for(int i=0; i<lista.size(); i++){
        	it = new Webscraping();
        	
        	// scrapeamos --> Class Webscraping
        	crip = it.getPricesAPI(lista.get(i));
        	if(crip.getStatus().equals("enabled")) {
        		criptos.add(crip);
            
        		acronimo = crip.getAcronimo();
        		total_market_cap = crip.getTotal_market_cap();
        		total_volume_24h = crip.getTotal_volume_24h();
        		total_volume_24h_reported = crip.getTotal_volume_24h_reported();
	            ultAct = crip.getUltimaActualizacion();
	            
	            
	            // QUERY1: �Existe la moneda "i"?
	    		sql = "SELECT * FROM criptomonedas";
	    		sql += " WHERE acronimo = ?";
	    	
	    		st = jdbcConnection.prepareStatement(sql);
	    		st.setString(1, acronimo);
	        	
	    		rs = st.executeQuery();
	    		// si FALSE --> INSERT
	    		if(rs.next() ) {
	    			sql = "UPDATE criptomonedas SET"; 
	    			sql += " total_market_cap = ?, total_volume_24h = ?, total_volume_24h_reported = ?, ultAct = ?";
	    			
	    			st = jdbcConnection.prepareStatement(sql);
	    			st.setFloat(1, total_market_cap);
	    			st.setFloat(2, total_volume_24h);
	    			st.setFloat(3, total_volume_24h_reported);
	       			st.setString(4, ultAct);
	    			
	    			stat = st.executeUpdate() > 0;
	    			st.close();
	    			
	    		} 
    	
        	} else {
        		stat = setCurrencyStatus(lista.get(i), "disabled");
        	}
        }

        disconnect();

        return criptos;
    }
	
	public static String getActualHour() {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		
		return formatter.format(date);
	}


}
