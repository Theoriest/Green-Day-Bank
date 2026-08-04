# Security Notes

## Authentication
- Store only hashed passwords in CSV
- Validate password at login
- Require password confirmation for all money-moving transactions

## Data Integrity
- Generate unique IDs for accounts and transactions
- Append immutable transaction logs
- Use safe CSV read/write workflow to reduce corruption risk

## Sensitive Operations
- Confirm withdrawals, transfers, sends, investments
- Restrict admin dashboard access by role

## Export Security
- Support password-protected Excel export for user transaction history
- Avoid exposing plaintext password in logs or files
