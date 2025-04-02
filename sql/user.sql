CREATE DATABASE xmall_user_db;
USE xmall_user_db;

CREATE TABLE user_base (
    user_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'User ID',
    user_account VARCHAR(255) NOT NULL UNIQUE COMMENT 'User account',
    user_password VARCHAR(255) NOT NULL COMMENT 'User password',
    user_salt VARCHAR(255) NOT NULL COMMENT 'User salt',
    user_login_time TIMESTAMP NULL DEFAULT NULL COMMENT 'Last login time',
    user_logout_time TIMESTAMP NULL DEFAULT NULL COMMENT 'Last logout time',
    user_login_ip VARCHAR(45) DEFAULT NULL COMMENT 'Login IP address',
    is_two_factor_enabled TINYINT(1) DEFAULT 0 COMMENT 'Two-factor authentication status',
    role_id INT UNSIGNED DEFAULT NULL COMMENT 'Role ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE SET NULL ON UPDATE CASCADE
) COMMENT='Base table for user accounts with roles';

CREATE TABLE user_info (
    user_id BIGINT UNSIGNED PRIMARY KEY COMMENT 'User ID', 
    user_account VARCHAR(255) NOT NULL COMMENT 'User account',
    user_nickname VARCHAR(255) DEFAULT NULL COMMENT 'User nickname',
    user_avatar VARCHAR(255) DEFAULT NULL COMMENT 'User avatar',
    user_state TINYINT UNSIGNED NOT NULL COMMENT 'User state: 0-Locked, 1-Activated, 2-Not Activated',
    user_mobile VARCHAR(20) DEFAULT NULL COMMENT 'Mobile phone number',
    user_gender TINYINT UNSIGNED DEFAULT 0 COMMENT 'User gender: 0-Secret, 1-Male, 2-Female',
    user_birthday DATE DEFAULT NULL COMMENT 'User birthday',
    user_email VARCHAR(255) DEFAULT NULL COMMENT 'User email',
    user_is_authentication TINYINT UNSIGNED NOT NULL COMMENT 'Authentication status: 0-Not Authenticated, 1-Authenticated',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (user_id) REFERENCES user_base(user_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='User detailed information table';

CREATE TABLE user_verify (
    verify_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'Verification ID',
    user_id BIGINT UNSIGNED NOT NULL COMMENT 'User ID', -- Khóa ngoại liên kết tới user_base
    verify_otp VARCHAR(6) NOT NULL COMMENT 'Verification OTP',
    verify_key VARCHAR(255) NOT NULL COMMENT 'Verification key (e.g., email or phone)',
    verify_key_hash VARCHAR(255) NOT NULL COMMENT 'Hashed verification key',
    verify_type TINYINT UNSIGNED DEFAULT 1 COMMENT 'Verification type: 1-Email, 2-Phone',
    is_verified TINYINT(1) DEFAULT 0 COMMENT 'Verification status: 0-No, 1-Yes',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT 'Deletion status: 0-No, 1-Yes',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (user_id) REFERENCES user_base(user_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='Table for OTP and verification data';

CREATE TABLE user_two_factor (
    two_factor_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'Two-factor ID',
    user_id BIGINT UNSIGNED NOT NULL COMMENT 'User ID', -- Khóa ngoại liên kết tới user_base
    two_factor_auth_type ENUM('SMS', 'EMAIL', 'APP') NOT NULL COMMENT 'Type of 2FA: SMS, Email, App',
    two_factor_auth_secret VARCHAR(255) NOT NULL COMMENT 'Secret for 2FA (e.g., TOTP key)',
    two_factor_phone VARCHAR(20) DEFAULT NULL COMMENT 'Phone number for SMS 2FA',
    two_factor_email VARCHAR(255) DEFAULT NULL COMMENT 'Email for email-based 2FA',
    is_active TINYINT(1) DEFAULT 1 COMMENT 'Is the 2FA method active?',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time',
    FOREIGN KEY (user_id) REFERENCES user_base(user_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='Two-factor authentication methods for users';

CREATE TABLE role (
    role_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT 'Role ID',
    role_name VARCHAR(255) NOT NULL UNIQUE COMMENT 'Role name',
    role_description TEXT DEFAULT NULL COMMENT 'Role description',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation time',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update time'
) COMMENT='Table for user roles';

-- Insert default roles
INSERT INTO user_roles (role_name, role_description) VALUES 
('Admin', 'Administrator with full access'),
('User', 'Regular user with limited access');