package com.rhkr8521.iccas_question.api.question.service;

import com.rhkr8521.iccas_question.api.question.domain.Question;
import com.rhkr8521.iccas_question.api.question.dto.QuestionDTO;
import com.rhkr8521.iccas_question.api.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final Random random = new Random();

    public Optional<QuestionDTO> getRandomQuestionByThemeAndStage(String theme, Long stage) {
        List<Question> questions = questionRepository.findByThemeAndStage(theme, stage);
        if (questions.isEmpty()) {
            return Optional.empty();
        }
        Question randomQuestion = questions.get(random.nextInt(questions.size()));
        QuestionDTO questionDTO = new QuestionDTO(
                randomQuestion.getQuestionId(),
                randomQuestion.getContent()
        );
        return Optional.of(questionDTO);
    }
}
