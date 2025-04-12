CREATE
DATABASE xmall_user_db;
USE
xmall_user_db;

CREATE TABLE user_role
(
    role_id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'Role ID',
    role_name        VARCHAR(255) NOT NULL UNIQUE COMMENT 'Role name',
    role_description TEXT      DEFAULT NULL COMMENT 'Role description',
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time'
) COMMENT='Table for user roles';

CREATE TABLE user_base
(
    user_id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'User ID',
    user_account          VARCHAR(255) NOT NULL UNIQUE COMMENT 'User account',
    user_password         VARCHAR(255) NOT NULL COMMENT 'User password',
    user_salt             VARCHAR(255) NOT NULL COMMENT 'User salt',
    user_login_time       TIMESTAMP NULL DEFAULT NULL COMMENT 'Last login time',
    user_logout_time      TIMESTAMP NULL DEFAULT NULL COMMENT 'Last logout time',
    user_login_ip         VARCHAR(45) DEFAULT NULL COMMENT 'Login IP address',
    is_two_factor_enabled TINYINT(1) DEFAULT 0 COMMENT 'Two-factor authentication status',
    role_id               BIGINT UNSIGNED DEFAULT NULL COMMENT 'Role ID',
    created_at            TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at            TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (role_id) REFERENCES user_role (role_id) ON DELETE SET NULL ON UPDATE CASCADE
) COMMENT='Base table for user accounts with roles';

CREATE TABLE user_info
(
    user_id                BIGINT UNSIGNED PRIMARY KEY COMMENT 'User ID',
    user_nickname          VARCHAR(255) DEFAULT NULL COMMENT 'User nickname',
    user_avatar            VARCHAR(255) DEFAULT NULL COMMENT 'User avatar',
    user_state             TINYINT UNSIGNED NOT NULL COMMENT 'User state: 0-Locked, 1-Activated, 2-Not Activated',
    user_mobile            VARCHAR(20)  DEFAULT NULL COMMENT 'Mobile phone number',
    user_gender            TINYINT UNSIGNED DEFAULT 0 COMMENT 'User gender: 0-Secret, 1-Male, 2-Female',
    user_birthday          TIMESTAMP    DEFAULT NULL COMMENT 'User birthday',
    user_email             VARCHAR(255) DEFAULT NULL COMMENT 'User email',
    user_is_authentication TINYINT UNSIGNED NOT NULL COMMENT 'Authentication status: 0-Not Authenticated, 1-Authenticated',
    user_city              VARCHAR(100) COMMENT 'User’s city of residence',
    user_job               VARCHAR(100) COMMENT 'User’s occupation',
    source_type            INT CHECK (source_type IN (0, 1)) COMMENT 'Registration source (0 - Direct, 1 - Social media)',
    user_integration       INT          DEFAULT 0 COMMENT 'Accumulated integration points for loyalty programs',
    user_growth            INT          DEFAULT 0 COMMENT 'User’s growth points for membership progression',
    created_at             TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at             TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (user_id) REFERENCES user_base (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='User detailed information table';

-- Table storing user level information
CREATE TABLE user_level
(
    level_id               BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for each user level',
    user_id                BIGINT UNSIGNED NOT NULL COMMENT 'User ID',
    level_name             VARCHAR(100)                                 NOT NULL COMMENT 'Name of the user level (e.g., Silver, Gold, Platinum)',
    growth_point           INT                                          NOT NULL COMMENT 'Required growth points to achieve this level',
    default_status         INT CHECK (default_status IN (0, 1))         NOT NULL COMMENT 'Indicates if this is the default level (0 - No, 1 - Yes)',
    free_freight_point     DECIMAL(10, 2) COMMENT 'Threshold for free shipping eligibility',
    comment_growth_point   INT                                          NOT NULL COMMENT 'Growth points earned for leaving product reviews',
    privilege_free_freight INT CHECK (privilege_free_freight IN (0, 1)) NOT NULL COMMENT 'Indicates if free shipping is granted (0 - No, 1 - Yes)',
    privilege_user_price   INT CHECK (privilege_user_price IN (0, 1))   NOT NULL COMMENT 'Indicates if special member pricing is available (0 - No, 1 - Yes)',
    privilege_birthday     INT CHECK (privilege_birthday IN (0, 1))     NOT NULL COMMENT 'Indicates if users get birthday rewards (0 - No, 1 - Yes)',
    note                   VARCHAR(255) COMMENT 'Additional remarks about the user level',
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (user_id) REFERENCES user_base (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='Defines different user membership levels and their privileges';

CREATE TABLE user_verify
(
    verify_id       BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'Verification ID',
    user_id         BIGINT UNSIGNED NOT NULL COMMENT 'User ID',
    verify_otp      VARCHAR(6)   NOT NULL COMMENT 'Verification OTP',
    verify_key      VARCHAR(255) NOT NULL COMMENT 'Verification key (e.g., email or phone)',
    verify_key_hash VARCHAR(255) NOT NULL COMMENT 'Hashed verification key',
    verify_type     TINYINT UNSIGNED DEFAULT 1 COMMENT 'Verification type: 1-Email, 2-Phone',
    is_verified     TINYINT(1) DEFAULT 0 COMMENT 'Verification status: 0-No, 1-Yes',
    is_deleted      TINYINT(1) DEFAULT 0 COMMENT 'Deletion status: 0-No, 1-Yes',
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (user_id) REFERENCES user_base (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='Table for OTP and verification data';

CREATE TABLE user_two_factor
(
    two_factor_id        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'Two-factor ID',
    user_id              BIGINT UNSIGNED NOT NULL COMMENT 'User ID',
    two_factor_auth_type ENUM('SMS', 'EMAIL', 'APP') NOT NULL COMMENT 'Type of 2FA: SMS, Email, App',
    target               VARCHAR(255) NOT NULL COMMENT 'Target for 2FA (e.g., phone number, email address)',
    is_active            TINYINT(1) DEFAULT 1 COMMENT 'Is the 2FA method active?',
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (user_id) REFERENCES user_base (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='Two-factor authentication methods for users';

-- Table storing user login history
CREATE TABLE user_login_log
(
    log_id     BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for each login record',
    user_id    BIGINT UNSIGNED                           NOT NULL COMMENT 'User ID of the person logging in',
    log_ip     VARCHAR(45)                      NOT NULL COMMENT 'IP address used for login',
    log_city   VARCHAR(100) COMMENT 'City from where the login occurred',
    login_type INT CHECK (login_type IN (1, 2)) NOT NULL COMMENT 'Login type (1 - Web, 2 - App)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (user_id) REFERENCES user_base (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='Tracks user login attempts and details';

-- Table storing user shipping addresses
CREATE TABLE user_receive_address
(
    receive_address_id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for the address',
    user_id            BIGINT UNSIGNED                               NOT NULL COMMENT 'User ID associated with this address',
    name               VARCHAR(100)                         NOT NULL COMMENT 'Recipient’s name',
    phone              VARCHAR(20)                          NOT NULL COMMENT 'Recipient’s phone number',
    post_code          VARCHAR(20) COMMENT 'Postal code of the address',
    province           VARCHAR(100) COMMENT 'Province/State of the address',
    city               VARCHAR(100) COMMENT 'City of the address',
    region             VARCHAR(100) COMMENT 'District/County of the address',
    detail_address     VARCHAR(255)                         NOT NULL COMMENT 'Complete address details',
    area_code          VARCHAR(50) COMMENT 'Area code',
    default_status     INT CHECK (default_status IN (0, 1)) NOT NULL COMMENT 'Indicates if this is the default address (0 - No, 1 - Yes)',
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (user_id) REFERENCES user_base (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='Stores user shipping addresses';

-- Table storing user statistics
CREATE TABLE user_statistics_info
(
    user_statistics_info_id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique ID for the statistics record',
    user_id                 BIGINT UNSIGNED NOT NULL COMMENT 'User ID associated with these statistics',
    consume_amount          DECIMAL(10, 2) DEFAULT 0 COMMENT 'Total amount spent by the user',
    order_count             INT            DEFAULT 0 COMMENT 'Total number of orders placed',
    comment_count           INT            DEFAULT 0 COMMENT 'Total number of product reviews written',
    return_order_count      INT            DEFAULT 0 COMMENT 'Total number of returned orders',
    login_count             INT            DEFAULT 0 COMMENT 'Total number of login attempts',
    created_at              TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at              TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (user_id) REFERENCES user_base (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='Stores user activity and engagement statistics';

-- Insert default roles
INSERT INTO user_roles (role_name, role_description)
VALUES ('Admin', 'Administrator with full access'),
       ('User', 'Regular user with limited access');