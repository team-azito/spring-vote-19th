package ceos.vote.auth.presentation.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public final class AuthDocs {

    private AuthDocs() {
    }

    public static RestDocumentationResultHandler generateCheckLoginUserDocumentation() {
        return document("auth-check/success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("id").description("회원 식별자"),
                        fieldWithPath("username").description("회원 아이디"),
                        fieldWithPath("email").description("회원 이메일"),
                        fieldWithPath("name").description("회원 이름"),
                        fieldWithPath("part").description("회원 파트"),
                        fieldWithPath("teamName").description("회원 팀 이름")
                )
        );
    }

    public static RestDocumentationResultHandler generateCheckLoginUserFailDocumentation() {
        return document("auth-check/fail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("message").description("에러 메시지")
                ));
    }
}
