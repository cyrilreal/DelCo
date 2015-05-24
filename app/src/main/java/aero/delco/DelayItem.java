package aero.delco;

public class DelayItem {
	private int id;
	private String title;
	private float number;
	private String content;
	
	public DelayItem() {

	}
	
	public DelayItem(int id, String title, float number, String content) {
		this.id = id;
		this.title = title;
		this.number = number;
		this.content = content;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the number
	 */
	public float getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(float number) {
		this.number = number;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
