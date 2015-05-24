package aero.delco;

public class DelayCode {
	private String category = null;
	private String number = null;
	private String content = null;

	public DelayCode() {

	}

	public DelayCode(String category, String number, String content) {
		this.category = category;
		this.number = number;
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
