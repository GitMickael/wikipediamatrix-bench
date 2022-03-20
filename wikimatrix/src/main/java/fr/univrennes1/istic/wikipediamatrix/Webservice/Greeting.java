package fr.univrennes1.istic.wikipediamatrix.Webservice;

import java.util.List;
import java.util.Map;

public class Greeting {

	private final long status_code;
	private final String message;
	private final List<String[][]> tables;
	private final Map<String,String> content;

	public Greeting(long status_code, String message, Map<String,String> content, List<String[][]> tables) {
		this.status_code = status_code;
		this.message = message;
		this.content = content;
		this.tables = tables;
	}

	public long getStatusCode() {
		return status_code;
	}

	public String getMessage() {
		return message;
	}

	public Map<String,String> getContent() {
		return content;
	}

	public List<String[][]> getTablesFromDoc() {
		return tables;
	}
}
