package jorge.rv.quizzz.csvloader;

import jorge.rv.quizzz.model.Answer;
import jorge.rv.quizzz.model.Question;
import jorge.rv.quizzz.model.Quiz;
import jorge.rv.quizzz.repository.AnswerRepository;
import jorge.rv.quizzz.repository.QuestionRepository;
import jorge.rv.quizzz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class ServiceCSV {

    @Autowired
    RepositoryCSV repositoryCSV;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    public void save(MultipartFile file) {
        try {
            List<EntityCSV> questions = HelperCSV.csvToEntity(file.getInputStream());
            repositoryCSV.save(questions);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }


    public void createQuiz(String quizName, String quizDesc) {
        System.out.println("Inside createQuiz");
        // 1. Create a new entry in the quiz table
        Quiz quiz = new Quiz();
        quiz.setName(quizName);
        quiz.setDescription(quizDesc);
        quiz.setIsPublished(true);
        quizRepository.save(quiz);

        Question question = new Question();
        question.setText("First questions");
        question.setOrder(1);
        question.setIsValid(true);
//        question.setCorrectAnswer(answer);
        question.setQuiz(quiz);
        questionRepository.save(question);

        Answer answer = new Answer();
        answer.setOrder(1);
        answer.setText("First answer");
        answer.setQuestion(question);
        answerRepository.save(answer);

        // 2. Populate questions from raw_data table to question table for with the quiz id from #1

        // 3. Set the correct_answer_id for each question from the raw_data answers column

        // 4. Populate answers from raw_dta table to answer table for each of the question ids

        // 5. Populate 3 randomly picked answers from raw_data table to answer table for each of the question ids

        // 6. Make sure the randomly picked answer is not the correct answer other there will be more than one correct answers for each question

    }

    public ByteArrayInputStream load() {
        List<EntityCSV> questions = repositoryCSV.findAll();

        ByteArrayInputStream in = HelperCSV.entityToCSV(questions);
        return in;
    }

    public List<EntityCSV> getAllQuestions() {
        return repositoryCSV.findAll();
    }

    public void replicateAnswers() {

        List<Question> questionsList = questionRepository.findAll();
        List<Answer> answerList = new ArrayList<>();

        for(Question question : questionsList){
            long questionId = question.getId();

            for(int i=2; i<=4; i++){
                long randomAnswerId = generateRandomAnswerId(questionId);
                Answer answer = answerRepository.findById(randomAnswerId);

                Answer newAnswer = new Answer();
                newAnswer.setOrder(i);
                newAnswer.setText(answer.getText());
                newAnswer.setQuestion(question);
                answerRepository.save(newAnswer);
//                answerList.add(answer);
            }
        }

//        for(Answer a : answerList){
//            System.out.println(a);
//        }


    }

    public long generateRandomAnswerId(long id){
        long num = 1 + (int)(Math.random() * ((60 - 1) + 1));
        if(num == id){
            generateRandomAnswerId(id);
        }

        return num;
    }
}
