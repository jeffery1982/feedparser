import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is for feedsource which should be mapping with json file.
 * @author Jeffery
 *
 */
public class FeedSource {
	private int id;
	private String title;
	private String description;
	private String uri;
	private String weight;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@JsonProperty("title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonProperty("uri")
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}

	@JsonProperty("weight")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
}
