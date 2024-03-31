CREATE TABLE employee (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL,
                          phone VARCHAR(20) NOT NULL,
                          joined DATE NOT NULL
);

INSERT INTO employee (name, email, phone, joined)
VALUES
    ('김철수', 'charles@clovf.com', '010-7531-2468', '2023-01-01'),
    ('박영희', 'matilda@clovf.com', '010-8765-4321', '2023-01-15'),
    ('홍길동', 'kildong.hone@clovf.com', '010-1234-5678', '2023-02-01');
