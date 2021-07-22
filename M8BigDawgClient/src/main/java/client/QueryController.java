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
	@GetMapping("/queryPanel")
	public String queryPanel(Model model) {
		Query query = new Query();
		model.addAttribute("query", query);

		//System.out.println("Query ID: " + query.getId());
		return "query";
	}
	@GetMapping("/castPanel")
	public String castPanel(Model model) {
		Query query = new Query();
		model.addAttribute("query", query);

		//System.out.println("Query ID: " + query.getId());
		return "cast";
	}




	@PostMapping("/query")
	public String querySubmit(@ModelAttribute Query query, Model model) throws IOException {
		String view = "queryResult";
		ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>> table;
		if(query.getType().equals("Text")) {
			query.setContent("bdtext(" + query.getContent()+ ");");
			table = this.queryEndpoint.executeQuery(model, query.getContent());
			System.out.println(query.getContent());
			view = "queryResultText";
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
		query.setContent("bdcatalog(select e.connection_properties as type, o.logical_db,"
				+ "o.name, o.fields from catalog.objects as o, "
				+ "catalog.engines as e, catalog.databases as d where "
				+ "o.physical_db=d.dbid and d.engine_id=e.eid limit 48)");
		System.out.println(query.getContent());
		table = this.queryEndpoint.executeJsonQuery(model, query.getContent());
		model.addAttribute("table", table);
		this.queryResult = table;
		return view;
	}

	@PostMapping("/cast")
	public String castSubmit(@ModelAttribute Query query, Model model) throws IOException {
		String view = "queryResult";
		ArrayList<Tuple2<Long, ArrayList<Tuple2<String,String>>>> table = null;
		if(query.getType().equals("AtR")) {

			query.setContent("bdrel(select * from bdcast( bdarray("
					+ query.getCast1() + "), " + query.getNameTable() +
					", " + query.getSchema() + ", relational))");
			System.out.println(query.getContent());
			table = this.queryEndpoint.executeJsonQuery(model, query.getContent());
		}
		else{
			if(query.getType().contentEquals("RtA")) {
				query.setContent("bdarray(scan(bdcast(bdrel("
						+ query.getCast1() + "), " + query.getNameTable() +
						", " + query.getSchema() + ", array)))");
				table = this.queryEndpoint.executeJsonQuery(model, query.getContent());


			}
			else {
				if(query.getType().contentEquals("TtR")) {
					query.setContent("bdrel(select * from bdcast(bdtext("
							+ query.getCast1() + "), " + query.getNameTable() +
							", " + query.getSchema() + ", relational))");
					table = this.queryEndpoint.executeJsonQuery(model, query.getContent());

				}
				else {
					if(query.getType().contentEquals("RtT")) {
						query.setContent("bdtext({ 'op' : 'scan', 'table' : 'bdcast(bdrel("
								+ query.getCast1() + "), " + query.getNameTable() +
								", " + query.getSchema() + ", text)'});");
						table = this.queryEndpoint.executeQuery(model, query.getContent());
						view = "queryResultText";
					}
				}
		}
	}


	model.addAttribute("table", table);
	this.queryResult = table;
	return view;
}












}