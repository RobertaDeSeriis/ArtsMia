package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.CoppiaId;

public class ArtsmiaDAO {

	public void listObject (Map<Integer,ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if (!idMap.containsKey(res.getInt("object_id"))){
					
				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				idMap.put(artObj.getId(), artObj);
				}
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
public List<ArtObject> getVertici (Map<Integer,ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result= new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if (!idMap.containsKey(res.getInt("object_id"))){
					
				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				idMap.put(artObj.getId(), artObj);
				result.add(artObj);
				}
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

public List<Adiacenza> getArchi (Map<Integer,ArtObject> idMap) {
	
	String sql = "SELECT e1.object_id, e2.object_id, COUNT(e1.exhibition_id) AS peso "
			+ "from exhibition_objects e1, exhibition_objects e2 "
			+ "WHERE e1.exhibition_id= e2.exhibition_id AND e1.object_id>e2.object_id "
			+ "GROUP BY e1.object_id, e2.object_id ";
	List<Adiacenza> result= new ArrayList<>();

	try {
		Connection conn = DBConnect.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		ResultSet res = st.executeQuery();
		while (res.next()) {

				
			Adiacenza artObj = new Adiacenza(idMap.get(res.getInt("e1.object_id")), idMap.get(res.getInt("e2.object_id")),res.getInt("peso"));
			result.add(artObj);
			
		}
		conn.close();
		
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return result;
}
	
}
