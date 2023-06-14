package jorge.rv.quizzz.csvloader;

import javax.persistence.*;
import javax.validation.constraints.Size;

@javax.persistence.Entity
@Table(name = "raw_data")
public class EntityCSV {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;

	@Size(min = 1, max = 100, message = "The question should be less than 100 characters")
	private String question;

	@Size(min = 1, max = 2000, message = "The answer should be less than 2000 characters")
	private String answer;

	public EntityCSV() {
	}

	public EntityCSV(long id, String question, String answer) {
		this.id = id;
		this.question = question;
		this.answer = answer;
	}

	public EntityCSV(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "EntityCSV{" +
				"id=" + id +
				", question='" + question + '\'' +
				", answer='" + answer + '\'' +
				'}';
	}
}
