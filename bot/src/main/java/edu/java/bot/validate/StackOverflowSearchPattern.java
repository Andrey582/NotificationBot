package edu.java.bot.validate;

import java.util.regex.Pattern;

public class StackOverflowSearchPattern extends LinkValidator {
    public StackOverflowSearchPattern(LinkValidator nextValidator) {
        super(nextValidator);
    }

    @Override
    protected Pattern getPattern() {
        return Pattern.compile("https://stackoverflow.com/search\\?q=[\\w+|+]+");
    }
}
