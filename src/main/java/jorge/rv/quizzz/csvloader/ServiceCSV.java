package jorge.rv.quizzz.csvloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;


@Service
public class ServiceCSV {

    @Autowired
    RepositoryCSV repositoryCSV;

    public void save(MultipartFile file) {
        try {
            List<EntityCSV> questions = HelperCSV.csvToEntity(file.getInputStream());
            repositoryCSV.save(questions);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<EntityCSV> questions = repositoryCSV.findAll();

        ByteArrayInputStream in = HelperCSV.entityToCSV(questions);
        return in;
    }

    public List<EntityCSV> getAllQuestions() {
        return repositoryCSV.findAll();
    }

    public void createQuiz() {

        // 1. Create a new entry in the quiz table

        // 2. Populate questions from raw_data table to question table for with the quiz id from #1

        // 3. Set the correct_answer_id for each question from the raw_data answers column

        // 4. Populate answers from raw_dta table to answer table for each of the question ids

        // 5. Populate 3 randomly picked answers from raw_data table to answer table for each of the question ids

        // 6. Make sure the randomly picked answer is not the correct answer other there will be more than one correct answers for each question

    }
}
