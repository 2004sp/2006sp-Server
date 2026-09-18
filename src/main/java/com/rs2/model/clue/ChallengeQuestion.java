package com.rs2.model.clue;

import java.util.HashMap;
import java.util.Map;

public enum ChallengeQuestion {
    QUESTION_2842(new String[]{"What is 57x89+23?"}, 2831, 2842, 5096),
    QUESTION_2844(new String[]{"What is 19 to the power of 3?"}, 2837, 2844, 6859),
    QUESTION_2846(new String[]{"How many fishermen are there on", "the fishing platform?"}, 2848, 2846, 11),
    QUESTION_2850(new String[]{"If x is 15 and y is 3,", " what is 3x + y?"}, 2849, 2850, 48),
    QUESTION_2852(new String[]{"How many people are waiting for", "the next Bard to perform?"}, 2853, 2852, 4),
    QUESTION_2854(new String[]{"How many animals are in", "the Ardougne Zoo?"}, 2855, 2854, 40),
    QUESTION_7275(new String[]{"How many buildings are there in", "the village?"}, 2858, 7275, 11),
    QUESTION_7277(new String[]{"How many bookcases are there ", "in the palace library?"}, 3607, 7277, 24),
    QUESTION_7279(new String[]{"How many pigeon cages are there ", "around the back of Jerico's house?"}, 3610, 7279, 3),
    QUESTION_7281(new String[]{"How many cannons does Lumbridge", "castle have?"}, 3611, 7281, 9),
    QUESTION_7283(new String[]{"I have 16 kebabs, I eat one myself and", "share the rest equally between 3 friends.", "How many do they have each?"}, 3613, 7283, 5),
    QUESTION_7269(new String[]{"How many flowers are there in", "the clearing below this platform?"}, 3566, 7269, 13),
    QUESTION_7271(new String[]{"How many gnomeballers have", "red patches on their uniforms?"}, 3568, 7271, 6),
    QUESTION_7273(new String[]{"How many banana trees are there", "in the plantation?"}, 3570, 7273, 33);

    private String[] questionLines;
    private int clueItemId;
    private int answerItemId;
    private int answerValue;
    private static Map questionsByClueItemId;
    private static Map questionsByAnswerItemId;

    static {
        questionsByClueItemId = new HashMap();
        questionsByAnswerItemId = new HashMap();
        ChallengeQuestion[] challengeQuestionArray = ChallengeQuestion.values();
        int length = challengeQuestionArray.length;
        int index = 0;
        while (index < length) {
            ChallengeQuestion challengeQuestion = challengeQuestionArray[index];
            questionsByClueItemId.put(challengeQuestion.clueItemId, challengeQuestion);
            questionsByAnswerItemId.put(challengeQuestion.answerItemId, challengeQuestion);
            ++index;
        }
    }

    public static ChallengeQuestion forClueItemId(int itemId) {
        return (ChallengeQuestion)((Object)questionsByClueItemId.get(itemId));
    }

    public static ChallengeQuestion forAnswerItemId(int itemId) {
        return (ChallengeQuestion)((Object)questionsByAnswerItemId.get(itemId));
    }

    private ChallengeQuestion(String[] questionLines, int clueItemId, int answerItemId, int answerValue) {
        this.questionLines = questionLines;
        this.clueItemId = clueItemId;
        this.answerItemId = answerItemId;
        this.answerValue = answerValue;
    }

    public final String[] getQuestionLines() {
        return this.questionLines;
    }

    public final int getClueItemId() {
        return this.clueItemId;
    }

    public final int getAnswerItemId() {
        return this.answerItemId;
    }

    public final int getAnswerValue() {
        return this.answerValue;
    }
}

