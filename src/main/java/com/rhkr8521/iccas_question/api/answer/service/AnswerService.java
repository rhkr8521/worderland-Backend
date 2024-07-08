package com.rhkr8521.iccas_question.api.answer.service;

import com.rhkr8521.iccas_question.api.answer.domain.Answer;
import com.rhkr8521.iccas_question.api.answer.repository.AnswerRepository;
import com.rhkr8521.iccas_question.api.question.domain.Question;
import com.rhkr8521.iccas_question.api.question.repository.QuestionRepository;
import com.rhkr8521.iccas_question.api.result.service.ResultService;
import com.rhkr8521.iccas_question.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ResultService resultService;

    @Transactional
    public boolean checkAnswer(Long questionId, String userId, String userAnswer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("문제 ID를 찾을 수 없습니다."));

        boolean isCorrect = question.getAnswer().equalsIgnoreCase(userAnswer);

        resultService.updateResult(userId, question.getTheme(), question.getStage(), isCorrect);

        Answer answer = Answer.builder()
                .question(question)
                .userId(userId)
                .result(isCorrect ? Answer.Result.OK : Answer.Result.FAIL)
                .build();

        answerRepository.save(answer);

        return isCorrect;
    }
}
