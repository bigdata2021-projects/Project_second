package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import scala.Tuple2;

public class QueryEndpoint {
	
	private String getAllFieldTextDB(String tableName) throws IOException {
		String fields = "";
		Set<String> hash_Set = new HashSet<String>(); 
		System.out.println("Table name: " + tableName);
		
		HttpEntity<String> request = new HttpEntity<>("bdtext({ 'op' : 'scan', 'table' : '" + tableName.replaceAll("\"", "") + "'});");
		RestTemplate restTemplate = new RestTemplate(); 
		ResponseEntity<String> response = restTemplate.postForEntity("http://0.0.0.0:8080/bigdawg/query/", request, String.class);
		String body = response.getBody();
		String[] lines = body.split("\n");
		for(String line : lines) {
			String[] splittedLine = line.split("\t");
			for(String s : splittedLine) {
				if(s.contains("=")) {			
					String[] splittedS = s.split("=");
					hash_Set.add(splittedS[0]);
				}

			}
		}
		
		for(String s : hash_Set) {
			fields+=s+",";
		}
		
		return fields.substring(0, fields.length()-1);
		
	}

	public ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>> executeJsonQuery(Model model, String query) throws IOException {
		
		HttpEntity<String> request = new HttpEntity<>(query);
		RestTemplate restTemplate = new RestTemplate(); 
		System.out.println("Executing query: " + query);
		ResponseEntity<String> response = restTemplate.postForEntity("http://0.0.0.0:8080/bigdawg/jsonquery/", request, String.class);
		System.out.println(response.getBody());

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		Iterator<JsonNode> iter = root.elements();
		Long id = 0L;
		ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>> table = new ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>>();
		while(iter.hasNext()) {
			JsonNode jsn = iter.next();
			Tuple2<Long, ArrayList<Tuple2<String,String>>> row = new Tuple2<Long, ArrayList<Tuple2<String,String>>>(id, new ArrayList<Tuple2<String,String>>());
			Iterator<Entry<String,JsonNode>> iter2 = jsn.fields();
			
			while(iter2.hasNext()) {
				
				
				Entry<String,JsonNode> e = iter2.next();
				String value = e.getValue().toString();
				System.out.println(value);
				JsonNode textDB = jsn.get("physical_db");
				if(textDB!=null && textDB.toString().toString().equals("7") && e.getKey().equals("fields")) {
					
					System.out.println("Text DB exploration ... ");
					System.out.println(jsn.get("name").toString());
					String fields = getAllFieldTextDB(jsn.get("name").toString());
					Tuple2<String,String> t = new Tuple2<String,String>(e.getKey(),fields);
					row._2.add(t);
					
					
				}else {
					Tuple2<String,String> t = new Tuple2<String,String>(e.getKey(),value);
					row._2.add(t);
				}
				
			}
			table.add(row);
			id++;

		}

		return table;
	}


	public ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>> executeQuery(Model model, String query) throws IOException {
		HttpEntity<String> request = new HttpEntity<>(query);
		RestTemplate restTemplate = new RestTemplate(); 
		System.out.println("Executing query: " + query);
		ResponseEntity<String> response = restTemplate.postForEntity("http://0.0.0.0:8080/bigdawg/query/", request, String.class);
		String body = response.getBody();

		ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>> table = new ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>>();
		String[] lines = body.split("\n");
		long id = 0;
		for(String line : lines) {
			String[] splittedLine = line.split("\t");
			Tuple2<Long, ArrayList<Tuple2<String,String>>> row = new Tuple2<Long, ArrayList<Tuple2<String,String>>>(id, new ArrayList<Tuple2<String,String>>());
			for(String s : splittedLine) {
				if(s.contains("=")) {			
					String[] splittedS = s.split("=");
					Tuple2<String,String> t = new Tuple2<String,String>(s,splittedS[1]);
					row._2.add(t);
				}

			}
			table.add(row);
			id++;
		}



		return table;
	}
	
	
}