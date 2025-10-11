Money Manager is a powerful and intuitive backend application designed to help you manage your personal finances with ease. Track your income and expenses, categorize your transactions, and gain insights into your spending habits to achieve your financial goals.

Features
User Authentication: Secure user registration and login system with JWT-based authentication.

Income and Expense Tracking: Easily add, view, and delete income and expense transactions.

Categorization: Organize your transactions by creating custom categories for both income and expenses.

Dashboard: Get a comprehensive overview of your financial status with a dashboard that displays your total balance, total income, total expenses, and recent transactions.

Filtering: Filter your transactions by type (income or expense), date range, and keyword.

Email Notifications: Receive daily email reminders to add your income and expenses, as well as a daily summary of your expenses.

Technologies Used
Java 17: The core programming language for the application.

Spring Boot 3.5.6: The framework used to build the application.

Spring Security: For handling user authentication and authorization.

Spring Data JPA: For interacting with the database.

MySQL/PostgreSQL: The application is configured to work with both MySQL and PostgreSQL databases.

Maven: For dependency management and building the project.

Lombok: To reduce boilerplate code.

JWT (Java Web Token): For securing the API endpoints.

API Endpoints
Method	Endpoint	Description
POST	/register->	Register a new user.
GET	/activate->	Activate a user's account.
POST	/login->	Log in a user and get a JWT token.
POST	/incomes->	Add a new income transaction.
GET	/incomes->	Get all income transactions for the current month.
DELETE	/incomes/{id}->	Delete an income transaction.
POST	/expenses->	Add a new expense transaction.
GET	/expenses->	Get all expense transactions for the current month.
DELETE	/expenses/{id}->	Delete an expense transaction.
POST	/categories->	Create a new category.
GET	/categories->	Get all categories for the current user.
GET	/categories/{type}->	Get all categories of a specific type (income/expense).
PUT	/categories/{categoryId}->	Update a category.
GET	/dashboard->	Get data for the dashboard.
POST	/filter->	Filter transactions.

Money Manager
Money Manager is a powerful and intuitive backend application designed to help you manage your personal finances with ease. Track your income and expenses, categorize your transactions, and gain insights into your spending habits to achieve your financial goals.

Features
User Authentication: Secure user registration and login system with JWT-based authentication.

Income and Expense Tracking: Easily add, view, and delete income and expense transactions.

Categorization: Organize your transactions by creating custom categories for both income and expenses.

Dashboard: Get a comprehensive overview of your financial status with a dashboard that displays your total balance, total income, total expenses, and recent transactions.

Filtering: Filter your transactions by type (income or expense), date range, and keyword.

Email Notifications: Receive daily email reminders to add your income and expenses, as well as a daily summary of your expenses.

Technologies Used
Java 17: The core programming language for the application.

Spring Boot 3.5.6: The framework used to build the application.

Spring Security: For handling user authentication and authorization.

Spring Data JPA: For interacting with the database.

MySQL/PostgreSQL: The application is configured to work with both MySQL and PostgreSQL databases.

Maven: For dependency management and building the project.

Lombok: To reduce boilerplate code.

JWT (Java Web Token): For securing the API endpoints.

API Endpoints
Method	Endpoint	Description
POST	/register	Register a new user.
GET	/activate	Activate a user's account.
POST	/login	Log in a user and get a JWT token.
POST	/incomes	Add a new income transaction.
GET	/incomes	Get all income transactions for the current month.
DELETE	/incomes/{id}	Delete an income transaction.
POST	/expenses	Add a new expense transaction.
GET	/expenses	Get all expense transactions for the current month.
DELETE	/expenses/{id}	Delete an expense transaction.
POST	/categories	Create a new category.
GET	/categories	Get all categories for the current user.
GET	/categories/{type}	Get all categories of a specific type (income/expense).
PUT	/categories/{categoryId}	Update a category.
GET	/dashboard	Get data for the dashboard.
POST	/filter	Filter transactions.

Getting Started

Prerequisites
Java 17
Maven
MySQL or PostgreSQL
