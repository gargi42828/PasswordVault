

🛡️ Project Overview
The Password Vault Desktop Application is a high-security, local-first credential management system. Built with a Zero-Knowledge Architecture, it ensures that sensitive data is encrypted and decrypted locally on the user's hardware. By leveraging AES-256-GCM and Argon2id, the application provides a hardened defense against unauthorized access and brute-force attacks.

🏗️ Technical Architecture
The project follows a modular, role-based architecture to ensure a separation of concerns between security logic and user interface:

Infrastructure Layer: Manages the SQLCipher database and normalized schema for high-performance lookups.

Security Specialist Layer: Implements Argon2id key derivation and AES-256 authenticated encryption.

Core Logic Layer: Handles secure CRUD operations, the password generator, and the model design for categories.

Data Service Layer: Manages advanced filtering, search functionality, and secure import/export routines.

Presentation Layer (UI): A lightweight Tauri/React interface with biometric integration (Windows Hello / TouchID).

🚀 Key Features
End-to-End Local Encryption: Master keys never leave the device.

Argon2id KDF: Industry-leading protection against GPU-accelerated cracking.

Biometric Unlock: Native support for fingerprint and facial recognition.

Password Auditor: Identifies weak, reused, or breached credentials.

Secure Clipboard: Automatically clears sensitive data from memory after 30 seconds.

🛠️ Tech Stack
Backend: Rust (Security Core) & Tauri

Frontend: React.js & Tailwind CSS

Database: SQLCipher (Encrypted SQLite)

Authentication: Native OS Biometrics & Argon2id

📂 Project Structure
Plaintext
src/
├── core/           # Cryptographic primitives (AES, Argon2id)
├── db/             # SQLCipher schema and migrations
├── auth/           # Session management and biometric hooks
├── ui/             # React components and dashboard logic
└── util/           # Password generator and strength validator
🚦 Getting Started
Clone the repo: git clone https://github.com/username/password-vault.git

Install dependencies: npm install

Build the app: npm run tauri build

Run locally: npm run tauri dev


