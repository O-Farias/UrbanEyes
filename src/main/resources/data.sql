INSERT INTO category (name) VALUES ('Iluminação pública');
INSERT INTO category (name) VALUES ('Saneamento básico');

INSERT INTO issue (title, description, status, created_at, updated_at, category_id)
VALUES ('Semáforo quebrado', 'Semáforo não funciona no cruzamento', 'PENDING', NOW(), NOW(), 1);
