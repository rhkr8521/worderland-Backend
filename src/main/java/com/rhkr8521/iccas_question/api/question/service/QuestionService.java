package com.rhkr8521.iccas_question.api.question.service;

import com.rhkr8521.iccas_question.api.question.domain.Question;
import com.rhkr8521.iccas_question.api.question.dto.ChatGPTRequestDTO;
import com.rhkr8521.iccas_question.api.question.dto.ChatGPTRequestImageDTO;
import com.rhkr8521.iccas_question.api.question.dto.ChatGPTResponseDTO;
import com.rhkr8521.iccas_question.api.question.dto.ChatGPTResponseImageDTO;
import com.rhkr8521.iccas_question.api.question.dto.QuestionDTO;
import com.rhkr8521.iccas_question.api.question.repository.QuestionRepository;
import com.rhkr8521.iccas_question.common.exception.InternalServerError;
import com.rhkr8521.iccas_question.common.exception.NotFoundException;
import com.rhkr8521.iccas_question.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final Random random = new Random();

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Value("${openai.api.image_url}")
    private String imageURL;

    @Value("${openai.api.image_size}")
    private String imageSize;

    @Value("${openai.api.image_count}")
    private int imageCount;

    public List<QuestionDTO> getRandomQuestionsByThemeAndStage(String theme, Long stage) {
        List<Question> questions = questionRepository.findByThemeAndStage(theme, stage);
        if (questions.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.shuffle(questions);
        return questions.stream()
                .limit(5)
                .map(question -> new QuestionDTO(question.getQuestionId(), question.getContent()))
                .collect(Collectors.toList());
    }

    public QuestionDTO getChatGPTQuestion(String theme) {
        String prompt = getPromptByTheme(theme);
        ChatGPTRequestDTO request = new ChatGPTRequestDTO(model, prompt);
        ChatGPTResponseDTO response = restTemplate.postForObject(apiURL, request, ChatGPTResponseDTO.class);

        if (response != null && !response.getChoices().isEmpty()) {
            ChatGPTResponseDTO.Choice choice = response.getChoices().get(0);
            String content = choice.getMessage().getContent();

            Pattern pattern = Pattern.compile("\\[sentence: (.*?), changed_word: (.*?), original_word: (.*?)]");
            Matcher matcher = pattern.matcher(content);

            if (matcher.find()) {
                String sentence = matcher.group(1);
                String changedWord = matcher.group(2);
                String originalWord = matcher.group(3);

                Question question = Question.builder()
                        .content(sentence + "+" + originalWord + "+" + changedWord)
                        .answer(changedWord)
                        .theme(theme)
                        .stage(3L)
                        .build();

                question = questionRepository.save(question);

                return new QuestionDTO(question.getQuestionId(), question.getContent());
            } else {
                throw new InternalServerError(ErrorStatus.CHATGPT_INCORRECT_RESPONSE + content);
            }
        } else {
            throw new InternalServerError(ErrorStatus.CHATGPT_RESPONSE_FAIL.getMessage());
        }
    }

    public List<QuestionDTO> getChatGPTImageQuestions(String theme) {
        List<Question> questions = questionRepository.findByThemeAndStage(theme, 2L).stream()
                .filter(question -> !question.getContent().startsWith("https"))
                .collect(Collectors.toList());

        if (questions.size() < 4) {
            throw new NotFoundException(ErrorStatus.NOT_FOUND_QUESTION.getMessage());
        }

        Collections.shuffle(questions);
        List<QuestionDTO> result = questions.stream()
                .limit(4)
                .map(question -> new QuestionDTO(question.getQuestionId(), question.getContent()))
                .collect(Collectors.toList());

        String fullPrompt = getRandomPrompt();
        String prompt = extractPrompt(fullPrompt);
        String answer = extractAnswerFromPrompt(fullPrompt);
        ChatGPTRequestImageDTO request = new ChatGPTRequestImageDTO(prompt, imageCount, imageSize);
        ChatGPTResponseImageDTO response = restTemplate.postForObject(imageURL, request, ChatGPTResponseImageDTO.class);

        if (response != null && !response.getData().isEmpty()) {
            String imageUrl = response.getData().get(0).getUrl();
            String content = imageUrl;

            Question question = Question.builder()
                    .content(content)
                    .answer(answer)
                    .theme(theme)
                    .stage(2L)
                    .build();

            question = questionRepository.save(question);
            result.add(new QuestionDTO(question.getQuestionId(), question.getContent()));
        } else {
            throw new InternalServerError(ErrorStatus.CHATGPT_RESPONSE_FAIL.getMessage());
        }

        return result;
    }

    private String getPromptByTheme(String theme) {
        switch (theme) {
            case "carousel":
                return "You are an expert in treating dyslexia. Write one infant level sentence for the Dyslexia Therapy Reading Practice using the theme of your experience at an amusement park carousel. You can write it wrong with one similar words on purpose. Don't change the word conversion too much. Please print it out in the form of [sentence: your_sentence, changed_word: your_changed_word, original_word: your_original_word]";
            case "ferris_wheel":
                return "You are an expert in treating dyslexia. Write one infant level sentence for the Dyslexia Therapy Reading Practice using the theme of your experience at an amusement park ferris wheel. You can write it wrong with one similar words on purpose. Don't change the word conversion too much. Please print it out in the form of [sentence: your_sentence, changed_word: your_changed_word, original_word: your_original_word]";
            case "roller_coaster":
                return "You are an expert in treating dyslexia. Write one infant level sentence for the Dyslexia Therapy Reading Practice using the theme of your experience at an amusement park roller coaster. You can write it wrong with one similar words on purpose. Don't change the word conversion too much. Please print it out in the form of [sentence: your_sentence, changed_word: your_changed_word, original_word: your_original_word]";
            default:
                throw new NotFoundException(ErrorStatus.NOT_FOUND_THEME.getMessage());
        }
    }

    private String getRandomPrompt() {
        List<String> prompts = List.of(
                "popcorn Illustrate.+popcorn",
                "hotdog Illustrate.+hotdog",
                "balloon Illustrate.+balloon",
                "carousel Illustrate.+carousel",
                "ticket Illustrate.+ticket",
                "pizza Illustrate.+pizza",
                "donut Illustrate.+donut",
                "bumpercar Illustrate.+bumpercar",
                "dog Illustrate.+dog",
                "cat Illustrate.+cat"
        );
        return prompts.get(random.nextInt(prompts.size()));
    }

    private String extractPrompt(String fullPrompt) {
        return fullPrompt.split("\\+")[0];
    }

    private String extractAnswerFromPrompt(String fullPrompt) {
        String[] parts = fullPrompt.split("\\+");
        return parts.length > 1 ? parts[1] : "";
    }
}
