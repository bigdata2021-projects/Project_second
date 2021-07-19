package client; 

public class Query {
	//test commit
	private static long NEXT_ID = 0;
	
    private long id;
    private String content;
    private String type;
    private String cast1;
    private String nameTable;
    private String schema;
    
    public String getCast1() {
		return cast1;
	}

	public void setCast1(String cast1) {
		this.cast1 = cast1;
	}

	public String getNameTable() {
		return nameTable;
	}

	public void setNameTable(String nameTable) {
		this.nameTable = nameTable;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Query() {
    	this.id = NEXT_ID;
    	NEXT_ID++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
