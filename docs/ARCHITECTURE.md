# Architecture

## Layers
- `model`: Domain entities and enums
- `service`: Banking workflows and business rules
- `repository`: Data access contracts and CSV implementations
- `security`: Password and transaction authentication
- `ui`: JavaFX controllers, FXML views, app state
- `util`: Shared utility helpers

## Data Flow
UI action -> Controller -> Service -> Repository -> CSV store

## Persistence
- `data/users.csv`
- `data/transactions.csv`

## Core Principles
- BigDecimal for monetary values
- No static business methods
- Clear separation of concerns
- Auditability via transaction records
