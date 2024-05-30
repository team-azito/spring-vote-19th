package ceos.vote.user.domain;

import java.util.Arrays;

import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.user.exception.UserException;

public enum TeamName {

    AZITO("Azito"),
    BEAT_BUDDY("BeatBuddy"),
    BULDOG("Buldog"),
    COUPLE_LOG("Couplelog"),
    TIG("TIG");

    private final String name;

    TeamName(String name) {
        this.name = name;
    }

    public static TeamName findTeamName(String teamName) {
        return Arrays.stream(TeamName.values())
                .filter(t -> t.name.equals(teamName))
                .findFirst()
                .orElseThrow(() -> new UserException(ExceptionCode.INVALID_TEAM_NAME));

    }
}
