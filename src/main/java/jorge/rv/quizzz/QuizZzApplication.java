package jorge.rv.quizzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "jorge.rv.quizzz")
public class QuizZzApplication {

//	@Autowired
//	private QuestionRepository questionRepository;
//
//	@Autowired
//	private RepositoryTempAnswers repositoryTempAnswers;
//
//	@Autowired
//	private AnswerRepository answerRepository;

	public static void main(String[] args) {
		SpringApplication.run(QuizZzApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner CommandLineRunnerBean() {
//		return (args) -> {
//			myQuestions();
//		};
//	}
//
//	public void myQuestions(){
//		List<Question> questions = getAllQuestions();
//		List<EntityTempAnswers> tempAnswers = getAllTempAnswers();
//
//		Answer answer = new Answer();
//
//		answer.setOrder(1);
//		answer.setQuestion(questions.get(0));
//		answer.setText("my set answer");
//		answerRepository.save(answer);
//
//	}
//
//	private List<Question> getAllQuestions(){
//		return questionRepository.findAll();
//	}
//
//	private List<EntityTempAnswers> getAllTempAnswers(){
//		return repositoryTempAnswers.findAll();
//	}
}
