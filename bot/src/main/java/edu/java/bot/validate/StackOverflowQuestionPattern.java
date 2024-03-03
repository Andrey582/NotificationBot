package edu.java.bot.validate;

import java.util.regex.Pattern;

public class StackOverflowQuestionPattern extends LinkValidator {
    public StackOverflowQuestionPattern(LinkValidator nextValidator) {
        super(nextValidator);
    }

    @Override
    protected Pattern getPatter() {
        return Pattern.compile("https://stackoverflow.com/questions/\\d+");
    }
}
