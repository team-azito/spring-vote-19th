-- users 테이블 생성
CREATE TABLE IF NOT EXISTS users (
   part TINYINT,
   team_name TINYINT,
   created_at TIMESTAMP(6),
   updated_at TIMESTAMP(6),
   user_id BIGINT AUTO_INCREMENT,
   email VARCHAR(255),
   name VARCHAR(255),
   password VARCHAR(255),
   username VARCHAR(255),
   PRIMARY KEY (user_id),
   CHECK (part BETWEEN 0 AND 1),
   CHECK (team_name BETWEEN 0 AND 4)
);

-- votes 테이블 생성
CREATE TABLE IF NOT EXISTS votes (
   team_name TINYINT,
   created_at TIMESTAMP(6),
   part_leader_id BIGINT,
   updated_at TIMESTAMP(6),
   vote_id BIGINT AUTO_INCREMENT,
   vote_user_id BIGINT,
   vote_type VARCHAR(31) NOT NULL,
   PRIMARY KEY (vote_id),
   CHECK (team_name BETWEEN 0 AND 4)
);

-- votes 테이블에 외래 키 추가
ALTER TABLE votes
    ADD CONSTRAINT FKdauw8dt535cafmab4fpx31fmc
        FOREIGN KEY (vote_user_id)
            REFERENCES users(user_id);

ALTER TABLE votes
    ADD CONSTRAINT FKgy6tc649wh51obix3hitcvht0
        FOREIGN KEY (part_leader_id)
            REFERENCES users(user_id);