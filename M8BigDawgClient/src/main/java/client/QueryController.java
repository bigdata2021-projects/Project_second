package client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scala.Tuple2;


@Controller
public class QueryController {

	private ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>> queryResult;
	private QueryEndpoint queryEndpoint;
	
	@PostConstruct
	public void initialize() {
		this.queryEndpoint = new QueryEndpoint();
	}

	@GetMapping("/")
	public String queryForm(Model model) {
		Query query = new Query();
		model.addAttribute("query", query);

		//System.out.println("Query ID: " + query.getId());
		return "home";
	}
	
	
	

	@PostMapping("/query")
	public String querySubmit(@ModelAttribute Query query, Model model) throws IOException {
		String view = "queryResult";
		ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>> table;
		if(query.getType().equals("Text")) {
			query.setContent("bdtext(" + query.getContent()+ ")");
			table = this.queryEndpoint.executeQuery(model, query.getContent());
			view = "variableFieldQueryResult";
		}else {
			if(query.getType().equals("Array")) {
				query.setContent("bdarray(" + query.getContent() + ")");
			}
			else {
				query.setContent("bdrel(" + query.getContent() + ")");
			}
			table = this.queryEndpoint.executeJsonQuery(model, query.getContent());

		}
		model.addAttribute("table", table);
		this.queryResult = table;
		return view;
	}
	
	@RequestMapping("/catalog")
	public String queryCatalog(@ModelAttribute Query query, Model model) throws IOException {
		String view = "catalog";
		ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>> table;
		query.setContent("bdcatalog(select * from catalog.objects)");
		table = this.queryEndpoint.executeJsonQuery(model, query.getContent());
		model.addAttribute("table", table);
		this.queryResult = table;
		return view;
	}







	
	

}