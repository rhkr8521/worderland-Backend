package com.rhkr8521.iccas_question.api.question.service;

import com.rhkr8521.iccas_question.api.question.domain.Question;
import com.rhkr8521.iccas_question.api.question.dto.QuestionDTO;
import com.rhkr8521.iccas_question.api.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final Random random = new Random();

    public List<QuestionDTO> getRandomQuestionsByThemeAndStage(String theme, Long stage) {
        List<Question> questions = questionRepository.findByThemeAndStage(theme, stage);
        if (questions.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.shuffle(questions);
        return questions.stream()
                .limit(10)
                .map(question -> new QuestionDTO(question.getQuestionId(), question.getContent()))
                .collect(Collectors.toList());
    }
}
