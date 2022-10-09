package entities;

public class Livre {

	private int isbn;
	private String title;
	private String author;
	private int year;
	private String category;
	
 public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getIsbn() {
		return isbn;
	}
	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Livre (int isbn, String title, String author, int year, String category) {
			super();
			this.title = title;
			this.author = author;
			this.isbn = isbn;
			this.year = year;
			this.category = category;
		}

	@Override
	public String toString() {
		return "Livre [isbn=" + isbn + ", title=" + title + ", author=" + author + ", year=" + year + ", category="
				+ category + "]";
	}
	
}
