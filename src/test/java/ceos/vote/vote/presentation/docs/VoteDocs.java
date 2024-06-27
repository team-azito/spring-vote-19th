package ceos.vote.vote.presentation.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public final class VoteDocs {

    private VoteDocs() {}

    public static RestDocumentationResultHandler getDemodayVoteResultDocument() {
        return document("vote/read/demoday/success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[].name").description("팀 이름"),
                        fieldWithPath("[].voteCount").description("득표수")
                ),
                responseBody()
        );
    }

    public static RestDocumentationResultHandler getPartLeaderVoteResultDocument() {
        return document("vote/read/part-leader/success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                queryParameters(
                        parameterWithName("part").description("BACK_END 또는 FRONT_END")
                ),
                responseFields(
                        fieldWithPath("[].username").description("파트장 로그인 아이디"),
                        fieldWithPath("[].name").description("파트장 후보 이름"),
                        fieldWithPath("[].voteCount").description("득표수")
                ),
                responseBody()
        );
    }

    public static RestDocumentationResultHandler getPartLeaderVoteResultFailDocument() {
        return document("vote/read/part-leader/fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );
    }

    public static RestDocumentationResultHandler voteDemodayDocument(String identifier) {
        return document(identifier,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("teamName").description("투표할 팀 이름. [AZITO, BEAT_BUDDY, BULDOG, CUPPLE_LOG, TIG] 중 하나")
                )
        );
    }

    public static RestDocumentationResultHandler votePartLeaderDocument(String identifier) {
        return document(identifier,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("partLeaderUsername").description("파트장으로 투표할 유저의 username. 같은 파트 사람이어야 한다.")
                )
        );
    }
}
