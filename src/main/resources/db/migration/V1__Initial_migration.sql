CREATE TABLE users (
                       id       BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name     VARCHAR(255) NOT NULL,
                       email    VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE addresses (
                           id        BIGINT AUTO_INCREMENT PRIMARY KEY,
                           street    VARCHAR(255) NOT NULL,
                           city      VARCHAR(255) NOT NULL,
                           province  VARCHAR(255) NOT NULL,
                           zipcode   VARCHAR(255) NOT NULL,
                           user_id   BIGINT       NOT NULL,
                           CONSTRAINT addresses_users_id_fk
                               FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE profiles (
                          id              BIGINT PRIMARY KEY,
                          bio             TEXT,
                          phone_number    VARCHAR(15),
                          date_of_birth   DATE,
                          loyalty_points  INT UNSIGNED DEFAULT 0,
                          FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE categories (
                            id   TINYINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
                          id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name          VARCHAR(255)  NOT NULL,
                          description   TEXT          NOT NULL,
                          price         DECIMAL(10,2) NOT NULL,
                          category_id   TINYINT       NOT NULL,
                          CONSTRAINT products_categories_id_fk
                              FOREIGN KEY (category_id) REFERENCES categories(id)
                                  ON DELETE RESTRICT
);

CREATE TABLE wishlist (
                          product_id BIGINT NOT NULL,
                          user_id    BIGINT NOT NULL,
                          CONSTRAINT pk_wishlist PRIMARY KEY (product_id, user_id)
);

ALTER TABLE wishlist
    ADD CONSTRAINT fk_wishlist_on_product
        FOREIGN KEY (product_id) REFERENCES products(id)
            ON DELETE CASCADE;

ALTER TABLE wishlist
    ADD CONSTRAINT fk_wishlist_on_user
        FOREIGN KEY (user_id) REFERENCES users(id);
