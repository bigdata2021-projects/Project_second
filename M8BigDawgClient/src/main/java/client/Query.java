package client; 

public class Query {
	//test commit
	private static long NEXT_ID = 0;
	
    private long id;
    private String content;
    private String type;
    
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
