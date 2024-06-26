package ceos.vote.user.presentation.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public final class UserDocs {

    private UserDocs() {
    }

    public static RestDocumentationResultHandler getDocument(String identifier) {
        return document(identifier,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("username").description("아이디"),
                        fieldWithPath("password").description("비밀번호"),
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("name").description("이름"),
                        fieldWithPath("part").description("파트"),
                        fieldWithPath("teamName").description("팀 이름")
                ));
    }

    public static RestDocumentationResultHandler getAllUserDocument(String identifier) {
        return document(identifier,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath("[].id").description("유저 식별자"),
                        fieldWithPath("[].username").description("아이디"),
                        fieldWithPath("[].email").description("이메일"),
                        fieldWithPath("[].name").description("이름"),
                        fieldWithPath("[].part").description("파트"),
                        fieldWithPath("[].teamName").description("팀 이름")
                ));
    }
}
