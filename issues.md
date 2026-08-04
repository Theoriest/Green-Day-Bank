# Green Day Bank - Implementation Issues

## 1) Project bootstrap (Maven + JavaFX + package skeleton)
**Goal**
Set up build tooling, Java version, JavaFX plugin, and baseline package/file structure.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/pom.xml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/BankingApp.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/AppLauncher.java`

**Acceptance criteria**
- Maven project resolves dependencies and compiles.
- JavaFX app launches a placeholder window.
- Package structure exists as defined.

**How to test**
- Run `mvn clean test`.
- Run `mvn javafx:run` and verify window opens.

---

## 2) Domain models and enums (User, Transaction, Fund, assets, roles)
**Goal**
Implement core entities/enums with BigDecimal-safe money fields and validation-ready structure.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/model/*`

**Acceptance criteria**
- User and Transaction models support all required attributes.
- Enums cover funds, transaction nature, affected assets, and roles.
- No monetary values use floating-point types.

**How to test**
- Add/execute model unit tests for constructors/getters/validation behavior.

---

## 3) CSV repositories (users + transactions + safe writes)
**Goal**
Implement repository interfaces and CSV-backed persistence with safe read/write behavior.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/repository/*`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/repository/csv/*`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/data/users.csv`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/data/transactions.csv`

**Acceptance criteria**
- Users and transactions can be created/read/updated/persisted.
- CSV headers are respected consistently.
- File corruption risk minimized via lock/temp-write strategy.

**How to test**
- Repository tests with temp files and concurrent-write simulation.

---

## 4) Authentication (signup/login/logout + password hashing)
**Goal**
Implement secure sign-up/login/logout flow using account number + password hash.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/service/AuthService.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/security/PasswordHasher.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/security/PasswordPolicy.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/state/SessionState.java`

**Acceptance criteria**
- Signup creates unique account number and stores hashed password.
- Login validates credentials and starts session.
- Logout clears session without closing app.

**How to test**
- Auth service tests for success/failure flows and password checks.

---

## 5) Account operations (deposit/withdraw/transfer/send with validation)
**Goal**
Implement all core account money operations with strict validation and error handling.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/service/AccountService.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/service/TransactionService.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/exception/*`

**Acceptance criteria**
- Deposit from cash to savings only.
- Withdraw from savings to cash with funds check.
- Transfer savings<->investment works both directions.
- Send money between users validates recipient and funds.

**How to test**
- Unit tests for positive and failure paths (insufficient funds, invalid amount, missing recipient).

---

## 6) Investment engine (fund purchase, growth on view, withdraw all)
**Goal**
Implement investment flows with appreciation logic and liquidation behavior.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/service/InvestmentService.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/model/Fund.java`

**Acceptance criteria**
- Users can invest from investment account into selected fund.
- Fund appreciation applied when balance is viewed.
- Withdraw-all moves all fund holdings back to investment account.

**How to test**
- Service tests for each fund rate and repeated balance-view behavior.

---

## 7) Transaction audit trail (ID generation + standardized log entries)
**Goal**
Ensure every money movement creates a traceable transaction record.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/service/TransactionService.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/util/IdGenerator.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/model/Transaction.java`

**Acceptance criteria**
- Each transaction has unique ID.
- Description/nature/asset/amount are consistently populated.
- Sender/receiver flows generate correct ledger entries.

**How to test**
- Transaction tests verifying record count/content per operation.

---

## 8) Transaction password confirmation for all money-moving operations
**Goal**
Require password re-authentication before executing financial actions.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/security/TransactionAuthenticator.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/service/*Service.java`

**Acceptance criteria**
- Deposit, withdraw, transfer, send, invest, withdraw-all require password confirmation.
- Wrong password prevents transaction.

**How to test**
- Unit tests with valid and invalid transaction-auth credentials.

---

## 9) GUI foundation (navigation shell + shared components + theme)
**Goal**
Build reusable JavaFX shell, navigation, and visual style system.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/*.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/css/app.css`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/state/NavigationState.java`

**Acceptance criteria**
- Sidebar/topbar layout established.
- Consistent theme applies across screens.
- Navigation works between views.

**How to test**
- Manual UI run-through and controller-level navigation tests.

---

## 10) Auth GUI screens (welcome/signup/login/logout flow)
**Goal**
Implement user-facing auth screens and interactions.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/welcome.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/signup.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/login.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/WelcomeController.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/SignUpController.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/LoginController.java`

**Acceptance criteria**
- Signup validates fields/password strength and shows generated account number.
- Login handles invalid credentials gracefully.
- Logout returns user to welcome/login without app shutdown.

**How to test**
- UI integration tests + manual checks for all auth paths.

---

## 11) Banking GUI screens (dashboard/accounts/investments/send)
**Goal**
Implement primary banking user journeys in GUI.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/dashboard.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/accounts.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/investments.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/send-money.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/DashboardController.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/AccountsController.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/InvestmentsController.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/SendMoneyController.java`

**Acceptance criteria**
- All core operations available from GUI.
- Balance refresh applies savings/fund growth rules.
- Validation and confirmation prompts are present.

**How to test**
- End-to-end manual scenario tests per operation.

---

## 12) Transactions GUI screen (history + filters + search)
**Goal**
Provide transparent transaction history browsing per user.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/transactions.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/TransactionsController.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/service/TransactionService.java`

**Acceptance criteria**
- Table includes timestamp, tx ID, description, nature, asset, amount.
- Filters and search return correct subsets.

**How to test**
- Populate sample transactions and validate filtered results.

---

## 13) Export to Excel (per-user history + password-protected file)
**Goal**
Allow users to export their transaction history in Excel format with password protection.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/service/ExportService.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/export.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/ExportController.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/pom.xml` (if dependency additions are needed)

**Acceptance criteria**
- Exported file contains only current user transactions.
- Workbook/file protection is enforced per selected password policy.

**How to test**
- Export and open file with correct/incorrect password attempts.

---

## 14) Admin dashboard (system-wide metrics + transaction table)
**Goal**
Implement admin-only analytics screen for overall system visibility.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/fxml/admin-dashboard.fxml`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/AdminDashboardController.java`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/service/AdminService.java`

**Acceptance criteria**
- Dashboard displays user count, tx count, and transaction list.
- Non-admin user cannot access admin screen.

**How to test**
- Role-based UI tests and manual role switching.

---

## 15) Error handling and user feedback standards
**Goal**
Ensure consistent, user-friendly error/success messaging across flows.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/exception/*`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/java/com/theoriest/greendaybank/ui/controller/*`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/main/resources/css/app.css`

**Acceptance criteria**
- Invalid inputs produce clear actionable messages.
- Critical actions show confirmations.
- No unhandled exceptions surface to user.

**How to test**
- Negative-path UI and service tests for each core operation.

---

## 16) Test suite implementation (unit/integration focus)
**Goal**
Build comprehensive automated tests for services, repositories, and security.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/test/java/com/theoriest/greendaybank/service/*`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/test/java/com/theoriest/greendaybank/repository/*`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/src/test/java/com/theoriest/greendaybank/security/*`

**Acceptance criteria**
- Core business rules covered with deterministic tests.
- CSV IO and auth/security paths tested.
- Build passes in CI with tests enabled.

**How to test**
- Run `mvn clean test` and verify pass rate + coverage target.

---

## 17) Demo data and migration scripts for CSV initialization
**Goal**
Provide reproducible sample data and initialization process for demos.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/data/users.csv`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/data/transactions.csv`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/docs/PROJECT_GUIDE.md`

**Acceptance criteria**
- CSV files can be initialized/reset predictably.
- Demo users/transactions documented.

**How to test**
- Reset data and validate app starts with expected baseline state.

---

## 18) Documentation completion (README + architecture + user guide)
**Goal**
Deliver complete project docs for contributors and reviewers.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/README.md`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/docs/ARCHITECTURE.md`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/docs/GUI_SPEC.md`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/docs/SECURITY_NOTES.md`

**Acceptance criteria**
- Setup, run, feature behavior, and architecture are fully documented.
- Security and export behavior clearly explained.

**How to test**
- Fresh contributor can run app using docs only.

---

## 19) Optional phase: simple web wrapper/demo hosting strategy
**Goal**
Define a practical plan to demonstrate the desktop app via web-accessible format.

**Files to work on**
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/docs/PROJECT_GUIDE.md`
- `/home/runner/work/Green-Day-Bank/Green-Day-Bank/docs/ARCHITECTURE.md`

**Acceptance criteria**
- Documented options (remote desktop stream, containerized desktop, or service facade).
- Trade-offs and security constraints clearly stated.

**How to test**
- Validate at least one demo path with a reproducible runbook.
