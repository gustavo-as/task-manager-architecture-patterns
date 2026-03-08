-- Usado pelo JDBC adapter e pelo JPA (ddl-auto=none se quiser controle manual)
CREATE TABLE IF NOT EXISTS tasks (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    completed   BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP    NOT NULL
);
