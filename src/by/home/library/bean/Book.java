package by.home.library.bean;

public class Book {
	private int id;
	private String title;
	private String author;
	private int year;
	private String genre;
	private boolean notForChild;
	private boolean isAvailable;
	private int owner; 
	
	public Book() {
		
	}

	public Book(int id, String title, String author, int year, String genre, boolean notForChild, boolean isAvailable, int owner) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.year = year;
		this.genre = genre;
		this.notForChild = notForChild;		
		this.isAvailable = isAvailable;
		this.owner = owner;
	}
	
	public Book(String title, String author, int year, String genre, boolean notForChild) {
		this.title = title;
		this.author = author;
		this.year = year;
		this.genre = genre;
		this.notForChild = notForChild;
		this.isAvailable = true;
		this.owner = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean isNotForChild() {
		return notForChild;
	}

	public void setNotForChild(boolean notForChild) {
		this.notForChild = notForChild;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + id;
		result = prime * result + (isAvailable ? 1231 : 1237);
		result = prime * result + (notForChild ? 1231 : 1237);
		result = prime * result + owner;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (id != other.id)
			return false;
		if (isAvailable != other.isAvailable)
			return false;
		if (notForChild != other.notForChild)
			return false;
		if (owner != other.owner)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() + " [id=" + id + ", title=" + title + ", author=" + author + ", year=" + year + ", genre=" + genre
				+ ", notForChild=" + notForChild + ", isAvailable=" + isAvailable + ", owner=" + owner + "]";
	}
	
		
}
