package edu.java.bot.validate;

import java.util.regex.Pattern;

public class GitHubReposPattern extends LinkValidator {

    public GitHubReposPattern(LinkValidator nextValidator) {
        super(nextValidator);
    }

    @Override
    protected Pattern getPatter() {
        return Pattern.compile("https://github.com/[\\w|-]+/[\\w|-]+");
    }
}
