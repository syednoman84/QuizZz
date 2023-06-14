package jorge.rv.quizzz.csvloader;


import jorge.rv.quizzz.controller.utils.RestVerifier;
import jorge.rv.quizzz.model.Answer;
import jorge.rv.quizzz.model.Question;
import jorge.rv.quizzz.repository.AnswerRepository;
import jorge.rv.quizzz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Random;

@RestController
public class ControllerCSV {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private RepositoryCSV repositoryTempAnswers;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	ServiceCSV fileService;

	@RequestMapping(value = "/dataloader", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> uploadFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("quizName") String quizName,
			@RequestParam("quizDesc") String quizDesc) {
		String message = "";

		if (HelperCSV.hasCSVFormat(file)) {
			try {
				fileService.save(file);
				System.out.println("File loaded. Creating Quiz");
				// add a call to a new service method to create a quiz and populate the questions and answers
				fileService.createQuiz(quizName, quizDesc);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> getFile() {
		String filename = "questions.csv";
		InputStreamResource file = new InputStreamResource(fileService.load());

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv"))
				.body(file);
	}

	/*@GetMapping("/api/answers/replicateanswers")
	@PreAuthorize("isAuthenticated()")
	public String replicateAnswers() {

		fileService.replicateAnswers();
		return "Completed.";
	}*/

	@RequestMapping(value = "/api/answers/replicateanswers", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	public String save() {

//		RestVerifier.verifyModelResult(result);
		fileService.replicateAnswers();
		return "Completed";

//		Question question = questionService.find(question_id);
//		return questionService.addAnswerToQuestion(answer, question);
	}

	/*List<Question> questions = getAllQuestions();
		List<EntityCSV> tempAnswers = getAllTempAnswers();

		int index = 0;
		Random rand = new Random();
		for (Question q : questions) {
			for (int i = 1; i <= 3; i++) {
				int randomIndex = rand.nextInt(tempAnswers.size());
				EntityCSV randomElement = tempAnswers.get(randomIndex);
				Answer answer = new Answer();
				answer.setOrder(i);
				answer.setQuestion(q);
				answer.setText(randomElement.getText());
				answerRepository.save(answer);
			}

			Answer fourthAnswer = new Answer();
			fourthAnswer.setOrder(4);
			fourthAnswer.setQuestion(q);
			fourthAnswer.setText(tempAnswers.get(index).getText());
			answerRepository.save(fourthAnswer);
			index++;
		}*/

//	private List<Question> getAllQuestions(){
//		return questionRepository.findAll();
//	}

//	private List<EntityCSV> getAllTempAnswers(){
//		return repositoryTempAnswers.findAll();
//	}

}
